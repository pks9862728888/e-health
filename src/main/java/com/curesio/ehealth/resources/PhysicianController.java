package com.curesio.ehealth.resources;

import com.curesio.ehealth.constants.UserImplementationConstants;
import com.curesio.ehealth.events.SendEmailVerificationMailEvent;
import com.curesio.ehealth.exceptions.*;
import com.curesio.ehealth.models.HttpResponse;
import com.curesio.ehealth.models.entities.User;
import com.curesio.ehealth.services.UserService;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/physician")
public class PhysicianController extends ExceptionHandlingController {

    private UserService userService;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PhysicianController(@Qualifier("userService") UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> userSignUp(
            @RequestPart("user_credentials") String userCredentials,
            @RequestPart("user_details") String userDetails,
            @RequestPart("physician_details") String physicianDetails,
            @RequestPart("document_type") String documentType,
            @RequestPart("id_front") MultipartFile idFront,
            @RequestPart("id_back") MultipartFile idBack) throws IOException, MagicMatchNotFoundException, MagicException, FileSizeTooLargeException, MagicParseException, FileTypeNotAllowedException, EmailExistsException, PasswordValidationException, UsernameValidationException, UsernameExistsException {

        User user = userService.validateAndRegisterResource(userCredentials, userDetails, documentType, idFront, idBack, physicianDetails);

        // Send email asynchronously
        applicationEventPublisher.publishEvent(
                new SendEmailVerificationMailEvent(this, user.getId(), user.getUserUniqueId(), user.getEmail()));

        // Create and send response object
        HttpResponse response = new HttpResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED, HttpStatus.CREATED.getReasonPhrase(), UserImplementationConstants.ACCOUNT_CREATED_MSG);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
