package com.curesio.ehealth.constants;

public interface SecurityConstants {

    // Security constants
    long EXPIRATION_TIME_IN_MILLISECONDS = 24 * 60 * 60 * 1000;
    String TOKEN_PREFIX = "Bearer ";
    String JWT_TOKEN_HEADER = "Jwt-Token";
    String ISSUER = "E-health Security Team";
    String AUDIENCE = "E-health Portal";
    String AUTHORITIES = "authorities";
    String OPTIONS_HTTP_METHOD = "options";

    // Messages
    String TOKEN_CANNOT_BE_VERIFIED_MSG = "Token can not be verified.";
    String FORBIDDEN_MSG = "You need to login to access this resource.";
    String ACCESS_DENIED_MSG = "You don't have enough permission to access this resource.";

    // Public urls
    String[] PUBLIC_URLS = {
            "/api/v1/user/sign-up",
            "/api/v1/physician/sign-up",
            "/api/v1/user/login",
            "/api/v1/user/verify-email/**",
            "/api/v1/user/request-reset-password",
            "/api/v1/user/reset-password",
            "/api/v1/user/request-unblock-account",
            "/api/v1/user/unblock-account"
    };
}
