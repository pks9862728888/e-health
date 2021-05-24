package com.curesio.ehealth.services;

import com.curesio.ehealth.exceptions.FileSizeTooLargeException;
import com.curesio.ehealth.exceptions.FileTypeNotAllowedException;
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

    User registerUser(String userCredentials, String userDetails, String documentType, MultipartFile idFront, MultipartFile idBack) throws IOException, MagicMatchNotFoundException, MagicException, MagicParseException, FileTypeNotAllowedException, FileSizeTooLargeException;

    Optional<EmailVerificationToken> generateVerificationTokenAndSaveToDb(long id);

    String generateUrlFromVerificationToken(String token, String userUniqueId);

    void updateMailSentStatus(long tokenId, boolean status, String reason);

}
