package com.curesio.ehealth.enumerations;

public enum AccountTypeEnum {
    USER("USR"),
    HOSPITAL("HOS"),
    PHYSICIAN("PHY"),
    LABORATORY("LAB"),
    PHARMACY("PHA");

    private String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
