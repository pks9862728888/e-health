package com.curesio.ehealth.services.impl;

import com.curesio.ehealth.enumerations.*;
import com.curesio.ehealth.exceptions.FileSizeTooLargeException;
import com.curesio.ehealth.exceptions.FileTypeNotAllowedException;
import com.curesio.ehealth.models.UserPrincipal;
import com.curesio.ehealth.models.entities.*;
import com.curesio.ehealth.repositories.*;
import com.curesio.ehealth.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jmimemagic.*;
import org.apache.commons.lang3.RandomStringUtils;
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
    private PasswordEncoder passwordEncoder;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserCustomDetailsRepository userCustomDetailsRepository, UserKycDocumentsRepository userKycDocumentsRepository, UserKycStatusRepository userKycStatusRepository, EmailVerificationTokenRepository emailVerificationTokenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCustomDetailsRepository = userCustomDetailsRepository;
        this.userKycDocumentsRepository = userKycDocumentsRepository;
        this.userKycStatusRepository = userKycStatusRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
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
    public User registerUser(String userCredentials, String userDetails, String documentType, MultipartFile idFront, MultipartFile idBack) throws IOException, MagicMatchNotFoundException, MagicException, MagicParseException, FileTypeNotAllowedException, FileSizeTooLargeException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Extract form fields from request and create objects
        User user = objectMapper.readValue(userCredentials, User.class);
        UserCustomDetails userCustomDetailsObj = objectMapper.readValue(userDetails, UserCustomDetails.class);

        // wire in values in user object
        user.setActive(USER_IS_ACTIVE_INITIALLY);
        user.setNonLocked(USER_ACCOUNT_IS_NON_LOCKED_INITIALLY);
        user.setPhoneVerified(USER_PHONE_IS_VERIFIED_INITIALLY);
        user.setEmail(user.getEmail().strip());
        user.setPhone(getStringIfExistsOrNull(user.getPhone()));
        user.setUserUniqueId(generateUniqueIdForUser(user.getAccountType()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        userRepository.saveAndFlush(user);

        // Save user details
        userCustomDetailsObj.setUser(user);
        userCustomDetailsObj.setCountry(CountryEnum.INDIA);
        userCustomDetailsRepository.saveAndFlush(userCustomDetailsObj);

        // Save user kyc details
        UserKycDocuments kycDocuments = new UserKycDocuments();
        kycDocuments.setUser(user);
        kycDocuments.setDocumentType(UserKycDocumentTypeEnum.valueOf(documentType));
        kycDocuments.setIdFrontFileType(getFileTypeIfFileTypeIsValid(idFront));
        kycDocuments.setIdBackFileType(getFileTypeIfFileTypeIsValid(idBack));
        kycDocuments.setIdFront(saveUserKycDocuments(user.getUserUniqueId(), idFront, "id_front"));
        kycDocuments.setIdBack(saveUserKycDocuments(user.getUserUniqueId(), idBack, "id_back"));
        userKycDocumentsRepository.saveAndFlush(kycDocuments);

        // Save user kyc status details
        UserKycStatus userKycStatus = new UserKycStatus();
        userKycStatus.setUser(user);
        userKycStatus.setUserKycStatus(UserKycStatusEnum.UNVERIFIED);
        userKycStatus.setResourceKycStatus(ResourceKycStatusEnum.NOT_APPLICABLE);
        userKycStatusRepository.saveAndFlush(userKycStatus);

        return user;
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
