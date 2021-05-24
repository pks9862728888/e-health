package com.curesio.ehealth.services.impl;

import com.curesio.ehealth.enumerations.*;
import com.curesio.ehealth.exceptions.*;
import com.curesio.ehealth.models.UserPrincipal;
import com.curesio.ehealth.models.entities.*;
import com.curesio.ehealth.repositories.*;
import com.curesio.ehealth.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jmimemagic.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.curesio.ehealth.constants.UserImplementationConstants.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Transactional
@Qualifier("userService")
public class UserServiceImpl implements UserService {

    @Value("${documents.kyc.path}")
    private String kycDocumentsPath;

    @Value("${kyc.max-file-size-in-mb}")
    private Integer kycMaxFileSize;

    @Value("${email-verification-token-expiry-days}")
    private int tokenExpiryDays;

    @Value("${server.offset-hours}")
    private int serverOffsetHours;

    @Value("${server.offset-minutes}")
    private int serverOffsetMinutes;

    @Value("${frontend-url}")
    private String frontEndUrl;

    private UserRepository userRepository;
    private UserCustomDetailsRepository userCustomDetailsRepository;
    private UserKycDocumentsRepository userKycDocumentsRepository;
    private UserKycStatusRepository userKycStatusRepository;
    private EmailVerificationTokenRepository emailVerificationTokenRepository;
    private PhysicianDetailsRepository physicianDetailsRepository;
    private PasswordEncoder passwordEncoder;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserCustomDetailsRepository userCustomDetailsRepository, UserKycDocumentsRepository userKycDocumentsRepository, UserKycStatusRepository userKycStatusRepository, EmailVerificationTokenRepository emailVerificationTokenRepository, PhysicianDetailsRepository physicianDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCustomDetailsRepository = userCustomDetailsRepository;
        this.userKycDocumentsRepository = userKycDocumentsRepository;
        this.userKycStatusRepository = userKycStatusRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.physicianDetailsRepository = physicianDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void setAbsoluteFilePaths() {
        this.kycDocumentsPath = System.getProperty("user.home") + kycDocumentsPath;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return new UserPrincipal(user.get());
        } else {
            LOGGER.error(NO_USER_FOUND_WITH_USERNAME_MSG + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_WITH_USERNAME_MSG + username);
        }
    }

    @Override
    public void validateWhetherEmailExistsAndCanBeUsedForNewUserRegistration(String email, AccountTypeEnum accountType) throws EmailExistsException {
        int emailCount;

        // An user can have multiple laboratories or pharmacies registered under same email
        if (accountType.equals(AccountTypeEnum.LABORATORY) || accountType.equals(AccountTypeEnum.PHARMACY)) {
            emailCount = userRepository.countAllByEmailAndNotAccountType(email, accountType);
        } else {
            emailCount = userRepository.countAllByEmail(email);
        }

        // If email exists then throw exception
        if (emailCount > 0) {
            LOGGER.error("Tried to register using email: {} for account type: {}, found email: {}", email, accountType, emailCount);
            throw new EmailExistsException(String.format(EMAIL_EXISTS_MSG, email));
        }
    }

    @Override
    public void validatePassword(String password) throws PasswordValidationException {
        if (password == null) {
            throw new PasswordValidationException(PASSWORD_CANNOT_BE_NULL_OR_EMPTY_MSG);
        }
        boolean isEmpty = true;
        boolean containUpperCase = false;
        boolean containLowerCase = false;
        boolean containDigit = false;
        boolean containSpecialCharacter = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (!Character.isWhitespace(ch)) {
                isEmpty = false;

                if (Character.isAlphabetic(ch)) {
                    if (Character.isUpperCase(ch)) {
                        containUpperCase = true;
                    } else if (Character.isLowerCase(ch)) {
                        containLowerCase = true;
                    }
                } else if (Character.isDigit(ch)) {
                    containDigit = true;
                } else {
                    containSpecialCharacter = true;
                }
            }

            if (containSpecialCharacter && containDigit && containUpperCase && containLowerCase && !isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            throw new PasswordValidationException(PASSWORD_CANNOT_BE_NULL_OR_EMPTY_MSG);
        } else if (!(containSpecialCharacter && containDigit && containUpperCase && containLowerCase)) {
            throw new PasswordValidationException(PASSWORD_SHOULD_CONTAIN_A_COMBINATION_OF_UPPER_CASE_LOWER_CASE_DIGIT_AND_SPECIAL_CHARACTER_MSG);
        } else if (password.strip().length() < PASSWORD_MIN_LENGTH) {
            throw new PasswordValidationException(PASSWORD_TOO_SHORT_MSG);
        }
    }

    @Override
    public void validateUsername(String username) throws UsernameExistsException, UsernameValidationException {
        if (StringUtils.isEmpty(username) || StringUtils.isWhitespace(username)) {
            throw new UsernameValidationException(USERNAME_CANNOT_BE_NULL_OR_EMPTY_MSG);
        }
        if (userRepository.countAllByUsername(username.strip()) > 0) {
            throw new UsernameExistsException(USERNAME_EXISTS_MSG);
        }
    }

    @Override
    public User validateAndRegisterUser(String userCredentials, String userDetails, String documentType, MultipartFile idFront, MultipartFile idBack) throws IOException, MagicMatchNotFoundException, MagicException, MagicParseException, FileTypeNotAllowedException, FileSizeTooLargeException, EmailExistsException, PasswordValidationException, UsernameExistsException, UsernameValidationException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Extract form fields from request and create objects
        User user = objectMapper.readValue(userCredentials, User.class);
        UserCustomDetails userCustomDetailsObj = objectMapper.readValue(userDetails, UserCustomDetails.class);

        // Validate user information
        validateUserInformation(user);

        // Save user
        saveUser(user);

        // Save user details
        saveUserDetails(userCustomDetailsObj, user);

        // Save user kyc details
        saveUserKycDocuments(documentType, idFront, idBack, user);

        // Save user kyc status details
        saveUserKycStatusDetails(user);

        return user;
    }

    @Override
    public User validateAndRegisterResource(String userCredentials, String userDetails, String documentType, MultipartFile idFront, MultipartFile idBack, String physicianDetails) throws IOException, MagicMatchNotFoundException, MagicException, MagicParseException, FileTypeNotAllowedException, FileSizeTooLargeException, EmailExistsException, PasswordValidationException, UsernameValidationException, UsernameExistsException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Extract form fields from request and create objects
        User user = objectMapper.readValue(userCredentials, User.class);
        UserCustomDetails userCustomDetailsObj = objectMapper.readValue(userDetails, UserCustomDetails.class);
        PhysicianDetails physicianDetailsObj = null;

        // Mapping resource specific details
        if (user.getAccountType().equals(AccountTypeEnum.PHYSICIAN)) {
            physicianDetailsObj = objectMapper.readValue(physicianDetails, PhysicianDetails.class);
        }

        // Validate user information
        validateUserInformation(user);

        // Save user
        saveUser(user);

        // Save user details
        saveUserDetails(userCustomDetailsObj, user);

        // Save user kyc details
        saveUserKycDocuments(documentType, idFront, idBack, user);

        // Save user kyc status details
        saveUserKycStatusDetails(user);

        // Save resource specific details
        if (user.getAccountType().equals(AccountTypeEnum.PHYSICIAN)) {
            savePhysicianDetails(physicianDetailsObj, user);
        }

        return user;
    }

    private void validateUserInformation(User user) throws EmailExistsException, UsernameValidationException, UsernameExistsException, PasswordValidationException {
        validateUsername(user.getUsername());
        validateWhetherEmailExistsAndCanBeUsedForNewUserRegistration(user.getEmail().strip(), user.getAccountType());
        validatePassword(user.getPassword());
    }

    private void saveUser(User user) {
        // wire in values in user object
        user.setActive(USER_IS_ACTIVE_INITIALLY);
        user.setNonLocked(USER_ACCOUNT_IS_NON_LOCKED_INITIALLY);
        user.setPhoneVerified(USER_PHONE_IS_VERIFIED_INITIALLY);
        user.setEmail(user.getEmail().strip());
        user.setPhone(getStringIfExistsOrNull(user.getPhone()));
        user.setUserUniqueId(generateUniqueIdForUser(user.getAccountType()));
        user.setPassword(passwordEncoder.encode(user.getPassword().strip()));
        user.setUsername(user.getUsername().strip());

        // Save user
        userRepository.saveAndFlush(user);
    }

    private void saveUserDetails(UserCustomDetails userDetails, User user) {
        // Save user details
        userDetails.setUser(user);
        userDetails.setCountry(CountryEnum.INDIA);
        userCustomDetailsRepository.saveAndFlush(userDetails);
    }

    private void saveUserKycDocuments(String documentType, MultipartFile idFront, MultipartFile idBack, User user) throws IOException, MagicMatchNotFoundException, FileSizeTooLargeException, MagicParseException, MagicException, FileTypeNotAllowedException {
        // Save user kyc details
        UserKycDocuments kycDocuments = new UserKycDocuments();
        kycDocuments.setUser(user);
        kycDocuments.setDocumentType(UserKycDocumentTypeEnum.valueOf(documentType));
        kycDocuments.setIdFrontFileType(getFileTypeIfFileTypeIsValid(idFront));
        kycDocuments.setIdBackFileType(getFileTypeIfFileTypeIsValid(idBack));
        kycDocuments.setIdFront(saveUserKycDocuments(user.getUserUniqueId(), idFront, "id_front"));
        kycDocuments.setIdBack(saveUserKycDocuments(user.getUserUniqueId(), idBack, "id_back"));
        userKycDocumentsRepository.saveAndFlush(kycDocuments);
    }

    private void saveUserKycStatusDetails(User user) {
        // Save user kyc status details
        UserKycStatus userKycStatus = new UserKycStatus();
        userKycStatus.setUser(user);
        userKycStatus.setUserKycStatus(UserKycStatusEnum.UNVERIFIED);
        userKycStatus.setResourceKycStatus(user.getAccountType().equals(AccountTypeEnum.USER) ? ResourceKycStatusEnum.NOT_APPLICABLE : ResourceKycStatusEnum.UNVERIFIED);
        userKycStatusRepository.saveAndFlush(userKycStatus);
    }

    private void savePhysicianDetails(PhysicianDetails physicianDetailsObj, User user) {
        physicianDetailsObj.setUser(user);
        physicianDetailsRepository.saveAndFlush(physicianDetailsObj);
    }

    @Override
    public Optional<EmailVerificationToken> generateVerificationTokenAndSaveToDb(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        // Generating and saving token in repository
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setUser(optionalUser.get());
        emailVerificationToken.setToken(UUID.randomUUID().toString());
        emailVerificationToken.setCreatedOn(OffsetDateTime.now());
        Date expiresOn = new Date(System.currentTimeMillis() + tokenExpiryDays * 24 * 60 * 60 * 1000);
        emailVerificationToken.setExpiresOn(expiresOn.toInstant().atOffset(ZoneOffset.ofHoursMinutes(serverOffsetHours, serverOffsetMinutes)));
        emailVerificationToken.setSent(false);
        emailVerificationTokenRepository.saveAndFlush(emailVerificationToken);

        return Optional.of(emailVerificationToken);
    }

    @Override
    public String generateUrlFromVerificationToken(String token, String userUniqueId) {
        return new StringBuilder(frontEndUrl)
                .append("user/")
                .append(userUniqueId)
                .append("/verify-account?token=")
                .append(token)
                .toString();
    }

    @Override
    public void updateMailSentStatus(long tokenId, boolean status, String reason) {
        Optional<EmailVerificationToken> optionalEmailVerificationToken = emailVerificationTokenRepository.findFirstById(tokenId);

        if (optionalEmailVerificationToken.isEmpty()) {
            LOGGER.error("No token found in EmailVerificationToken table with id: " + tokenId);
            return;
        }

        EmailVerificationToken verificationToken = optionalEmailVerificationToken.get();
        verificationToken.setSent(status);
        verificationToken.setReason(reason.length() > 0 ? reason : null);
        emailVerificationTokenRepository.saveAndFlush(verificationToken);
    }

    private KycDocumentFileTypeEnum getFileTypeIfFileTypeIsValid(MultipartFile file) throws IOException, MagicParseException, MagicMatchNotFoundException, FileTypeNotAllowedException, FileSizeTooLargeException, MagicException {
        if (file == null) {
            return null;
        }

        // Validating file size
        if (file.getSize() > kycMaxFileSize * 1000 * 1000) {
            throw new FileSizeTooLargeException(String.format(FILE_SIZE_TOO_LARGE_MSG, kycMaxFileSize));
        }

        // Validating file mime types
        MagicMatch magicMatch = Magic.getMagicMatch(file.getBytes());
        String mimeType = magicMatch.getMimeType();

        if (mimeType.equalsIgnoreCase(MimeTypeUtils.IMAGE_JPEG_VALUE) || mimeType.equalsIgnoreCase(MimeTypeUtils.IMAGE_PNG_VALUE)) {
            return KycDocumentFileTypeEnum.IMG;
        } else if (mimeType.equalsIgnoreCase(PDF_MIME_TYPE)) {
            return KycDocumentFileTypeEnum.PDF;
        } else {
            throw new FileTypeNotAllowedException(FILE_TYPE_NOT_ALLOWED_MSG);
        }
    }

    private String saveUserKycDocuments(String userUniqueId, MultipartFile file, String filename) throws IOException {
        // Save the document
        if (file != null) {
            Path userFolder = Paths.get(kycDocumentsPath + userUniqueId).toAbsolutePath().normalize();

            // Create user directory if it does not exist
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED_MSG + userFolder);
            }

            // Save file
            String originalFileName = file.getOriginalFilename();
            filename += originalFileName.substring(originalFileName.lastIndexOf("."));
            Files.copy(file.getInputStream(), userFolder.resolve(filename), REPLACE_EXISTING);
            return filename;
        }
        return null;
    }

    private String getStringIfExistsOrNull(String str) {
        if (str == null) {
            return null;
        }
        return str.strip().length() > 0 ? str.strip() : null;
    }

    private String generateUniqueIdForUser(AccountTypeEnum accountType) {
        StringBuilder userId = new StringBuilder(accountType.getValue());
        int randomLength = USER_ID_LENGTH - userId.length();

        while (true) {
            String id = RandomStringUtils.randomAlphanumeric(randomLength, randomLength).toUpperCase();

            // Check whether id exists
            if (userRepository.countAllByUserUniqueId(userId.toString() + id) == 0) {
                userId.append(id);
                break;
            }
        }

        return userId.toString();
    }
}
