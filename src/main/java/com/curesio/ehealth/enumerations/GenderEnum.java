package com.curesio.ehealth.enumerations;

public enum GenderEnum {
    MALE('M'),
    FEMALE('F'),
    OTHERS('O');

    Character value;

    GenderEnum(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }
}
