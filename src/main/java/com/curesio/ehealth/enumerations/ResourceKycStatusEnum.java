package com.curesio.ehealth.enumerations;

public enum ResourceKycStatusEnum {
    VERIFIED('V'),
    UNVERIFIED('U'),
    REQUESTED_MORE_INFO('R'),
    NOT_APPLICABLE('N');

    Character value;

    ResourceKycStatusEnum(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }
}
