package com.curesio.ehealth.resources;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.curesio.ehealth.exceptions.*;
import com.curesio.ehealth.models.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlingController {

    private static final String ACCOUNT_LOCKED_MSG = "Your account has been locked. Please contact administrator.";
    private static final String METHOD_IS_NOT_ALLOWED_MSG = "This request method is not allowed on this endpoint. Please send a %s response";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An internal server error occurred while processing the request.";
    private static final String INCORRECT_CREDENTIALS_MSG = "Username / password incorrect. Please try again.";
    private static final String ACCOUNT_DISABLED_MSG = "Your account has been disabled. If this is an error, please contact administrator.";
    private static final String ERROR_PROCESSING_FILE_MSG = "Error occurred while processing file.";
    private static final String NOT_ENOUGH_PERMISSION_MSG = "You do not have enough permission.";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> getAccountDisabledResponse() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED_MSG);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS_MSG);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        return createHttpResponse(HttpStatus.FORBIDDEN, NOT_ENOUGH_PERMISSION_MSG);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException() {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, ACCOUNT_LOCKED_MSG);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistsException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<HttpResponse> usernameExistsException(UsernameExistsException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED_MSG, supportedMethod));
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> ioException(IOException exception) {
        LOGGER.error(exception.getMessage());
        LOGGER.error(Arrays.toString(exception.getStackTrace()));
        return createHttpResponse(HttpStatus.BAD_REQUEST, ERROR_PROCESSING_FILE_MSG);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpResponse> resourceNotFoundException(ResourceNotFoundException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        LOGGER.error(Arrays.toString(exception.getCause().getStackTrace()));
        return createHttpResponse(HttpStatus.BAD_REQUEST, INTERNAL_SERVER_ERROR_MSG);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(
                httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase(),
                message
        );
        return new ResponseEntity<>(httpResponse, httpStatus);
    }


}
