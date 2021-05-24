package com.curesio.ehealth.services;

import com.curesio.ehealth.enumerations.AccountTypeEnum;
import com.curesio.ehealth.exceptions.*;
import com.curesio.ehealth.models.entities.EmailVerificationToken;
import com.curesio.ehealth.models.entities.User;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void validateWhetherEmailExistsAndCanBeUsedForNewUserRegistration(String email, AccountTypeEnum accountType) throws EmailExistsException;

    void validatePassword(String password) throws PasswordValidationException;

    void validateUsername(String username) throws UsernameExistsException, UsernameValidationException;

    User validateAndRegisterUser(String userCredentials, String userDetails, String documentType, MultipartFile idFront, MultipartFile idBack) throws IOException, MagicMatchNotFoundException, MagicException, MagicParseException, FileTypeNotAllowedException, FileSizeTooLargeException, EmailExistsException, PasswordValidationException, UsernameExistsException, UsernameValidationException;

    User validateAndRegisterResource(String userCredentials, String userDetails, String documentType, MultipartFile idFront, MultipartFile idBack, String physicianDetails) throws IOException, MagicMatchNotFoundException, MagicException, MagicParseException, FileTypeNotAllowedException, FileSizeTooLargeException, EmailExistsException, PasswordValidationException, UsernameValidationException, UsernameExistsException;

    Optional<EmailVerificationToken> generateVerificationTokenAndSaveToDb(long id);

    String generateUrlFromVerificationToken(String token, String userUniqueId);

    void updateMailSentStatus(long tokenId, boolean status, String reason);

}
