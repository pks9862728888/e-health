package com.curesio.ehealth.enumerations;

public enum UserKycStatusEnum {
    VERIFIED('V'),
    UNVERIFIED('U'),
    REQUESTED_MORE_INFO('R');

    Character value;

    UserKycStatusEnum(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }
}
