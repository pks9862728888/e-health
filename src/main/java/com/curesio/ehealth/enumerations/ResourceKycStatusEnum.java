package com.curesio.ehealth.enumerations;

public enum ResourceKycStatusEnum {
    VERIFIED("V"),
    UNVERIFIED("U"),
    REQUESTED_MORE_INFO("R"),
    NOT_APPLICABLE("N");

    String value;

    ResourceKycStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
