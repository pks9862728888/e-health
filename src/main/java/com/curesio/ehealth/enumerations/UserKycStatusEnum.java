package com.curesio.ehealth.enumerations;

public enum UserKycStatusEnum {
    VERIFIED("V"),
    UNVERIFIED("U"),
    REQUESTED_MORE_INFO("R");

    String value;

    UserKycStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
