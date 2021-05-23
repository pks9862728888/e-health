package com.curesio.ehealth.enumerations;

public enum UserKycDocumentTypeEnum {
    AADHAR("AAD"),
    PASSPORT("PASS"),
    PAN("PAN"),
    DRIVING_LICENSE("LIC");

    String value;

    UserKycDocumentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}