package com.curesio.ehealth.enumerations;

public enum CountryEnum {
    INDIA("IN");

    private String value;

    CountryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
