package com.curesio.ehealth.enumerations;

public enum AccountTypeEnum {
    USER("USR"),
    HOSPITAL("HOS"),
    PHYSICIAN("PHY"),
    LABORATORY("LAB"),
    PHARMACY("PHA");

    private String name;

    AccountTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
