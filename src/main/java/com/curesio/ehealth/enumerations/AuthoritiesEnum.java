package com.curesio.ehealth.enumerations;

import java.util.ArrayList;

import static com.curesio.ehealth.constants.Authorities.*;

public enum AuthoritiesEnum {
    ROLE_USER(USER_AUTHORITIES, "ROLE_USER"),
    ROLE_HOSPITAL_ADMIN(HOSPITAL_ADMIN_AUTHORITIES, "ROLE_HOSPITAL_ADMIN"),
    ROLE_HOSPITAL_STAFF(HOSPITAL_STAFF_AUTHORITIES, "ROLE_HOSPITAL_STAFF"),
    ROLE_PHYSICIAN_ADMIN(PHYSICIAN_ADMIN_AUTHORITIES, "ROLE_PHYSICIAN_ADMIN"),
    ROLE_PHYSICIAN_STAFF(PHYSICIAN_STAFF_AUTHORITIES, "ROLE_PHYSICIAN_STAFF"),
    ROLE_PHARMACY_ADMIN(PHARMACY_ADMIN_AUTHORITIES, "ROLE_PHARMACY_ADMIN"),
    ROLE_PHARMACY_STAFF(PHARMACY_STAFF_AUTHORITIES, "ROLE_PHARMACY_STAFF"),
    ROLE_LABORATORY_ADMIN(LABORATORY_ADMIN_AUTHORITIES, "ROLE_LABORATORY_ADMIN"),
    ROLE_LABORATORY_STAFF(LABORATORY_STAFF_AUTHORITIES, "ROLE_LABORATORY_STAFF"),
    ROLE_ADMIN(ADMIN_AUTHORITIES, "ROLE_ADMIN"),
    ROLE_ACCOUNT_VERIFICATION_STAFF(ACCOUNT_VERIFICATION_STAFF_AUTHORITIES, "ROLE_ACCOUNT_VERIFICATION_STAFF");

    private ArrayList<String> authorities;
    private String value;

    AuthoritiesEnum(ArrayList<String> authorities, String value) {
        this.authorities = authorities;
        this.value = value;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
