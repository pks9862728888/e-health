package com.curesio.ehealth.enumerations;

public enum CurrencyEnum {
    INR("INR");

    private String value;

    CurrencyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
