package com.curesio.ehealth.resources;

import com.curesio.ehealth.events.SendEmailVerificationMailEvent;
import com.curesio.ehealth.exceptions.FileSizeTooLargeException;
import com.curesio.ehealth.exceptions.FileTypeNotAllowedException;
import com.curesio.ehealth.models.entities.User;
import com.curesio.ehealth.services.UserService;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends ExceptionHandlingController {

    private UserService userService;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserController(@Qualifier("userService") UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User userSignUp(
            @RequestPart("user_credentials") String userCredentials,
            @RequestPart("user_details") String userDetails,
            @RequestPart("document_type") String documentType,
            @RequestPart("id_front") MultipartFile idFront,
            @RequestPart("id_back") MultipartFile idBack) throws IOException, MagicMatchNotFoundException, MagicException, FileSizeTooLargeException, MagicParseException, FileTypeNotAllowedException {

        User user = userService.registerUser(userCredentials, userDetails, documentType, idFront, idBack);

        // Send email asynchronously
        applicationEventPublisher.publishEvent(new SendEmailVerificationMailEvent(this, user.getId()));

        return user;
    }
}
