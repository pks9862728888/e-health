package com.curesio.ehealth.constants;

public interface UserImplementationConstants {

    // Implementation logic constants
    int PASSWORD_MIN_LENGTH = 8;
    int USER_ID_LENGTH = 10;
    boolean USER_IS_ACTIVE_INITIALLY = false;
    boolean USER_ACCOUNT_IS_NON_LOCKED_INITIALLY = true;
    boolean USER_PHONE_IS_VERIFIED_INITIALLY = false;
    String PDF_MIME_TYPE = "application/pdf";

    // Implementation messages
    String ACCOUNT_CREATED_MSG = "Account creation successful! Please activate your account by clicking on the link mailed to you. After that please wait till our backend team verifies your KYC details.";
    String NO_USER_FOUND_WITH_USERNAME_MSG = "No user found with username: ";
    String DIRECTORY_CREATED_MSG = "Directory created: ";
    String FILE_TYPE_NOT_ALLOWED_MSG = "File type not allowed. Allowed file types are: .png, .jpeg, .pdf";
    String FILE_SIZE_TOO_LARGE_MSG = "File type too large. Max allowed file size is: %s Mb";
    String EMAIL_EXISTS_MSG = "This email (%s) is already registered in our portal. Try resetting the password by clicking Login -> Forgot Password";
    String PASSWORD_CANNOT_BE_NULL_OR_EMPTY_MSG = "Password can not be null or empty or blank.";
    String PASSWORD_SHOULD_CONTAIN_A_COMBINATION_OF_UPPER_CASE_LOWER_CASE_DIGIT_AND_SPECIAL_CHARACTER_MSG = "Password should contain at least one upper case, one lower case, a digit, and one special character.";
    String PASSWORD_TOO_SHORT_MSG = String.format("Password too short. Password should be minimum of %s characters.", PASSWORD_MIN_LENGTH);
    String USERNAME_EXISTS_MSG = "This username is taken.";
    String USERNAME_CANNOT_BE_NULL_OR_EMPTY_MSG = "Username can not be null or empty or blank.";
    String FIELDS_ARE_REQUIRED_MSG = "Input fields mismatch. Required fields are: %s";
}
