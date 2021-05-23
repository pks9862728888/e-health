package com.curesio.ehealth.enumerations;

public enum AccountTypeEnum {
    USER("USR"),
    HOSPITAL("HOS"),
    PHYSICIAN("PHY"),
    LABORATORY("LAB"),
    PHARMACY("PHA"),
    HOSPITAL_STAFF("HOS_ST"),
    PHYSICIAN_STAFF("PHY_ST"),
    LABORATORY_STAFF("LAB_ST"),
    PHARMACY_STAFF("PHA_ST");

    private String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
