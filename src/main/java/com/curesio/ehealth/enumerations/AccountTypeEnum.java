package com.curesio.ehealth.enumerations;

public enum AccountTypeEnum {
    USER("USR"),
    HOSPITAL("HOS"),
    PHYSICIAN("PHY"),
    LABORATORY("LAB"),
    PHARMACY("PHA"),
    HOSPITAL_STAFF("HOS_STAFF"),
    PHYSICIAN_STAFF("PHY_STAFF"),
    LABORATORY_STAFF("LAB_STAFF"),
    PHARMACY_STAFF("PHA_STAFF");

    private String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
