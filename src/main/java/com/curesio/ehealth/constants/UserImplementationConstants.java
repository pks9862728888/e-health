package com.curesio.ehealth.constants;

public interface UserImplementationConstants {

    String NO_USER_FOUND_WITH_USERNAME_MSG = "No user found with username: ";
    String DIRECTORY_CREATED_MSG = "Directory created: ";
    String FILE_TYPE_NOT_ALLOWED_MSG = "File type not allowed. Allowed file types are: .png, .jpeg, .pdf";
    String FILE_SIZE_TOO_LARGE_MSG = "File type too large. Max allowed file size is: %s Mb";
    int USER_ID_LENGTH = 10;
    boolean USER_IS_ACTIVE_INITIALLY = false;
    boolean USER_ACCOUNT_IS_NON_LOCKED_INITIALLY = true;
    boolean USER_PHONE_IS_VERIFIED_INITIALLY = false;
    String PDF_MIME_TYPE = "application/pdf";

}
