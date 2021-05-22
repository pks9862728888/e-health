package com.curesio.ehealth.enumerations;

public enum GenderEnum {
    Male("M"),
    Female("F"),
    Others("O");

    String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
