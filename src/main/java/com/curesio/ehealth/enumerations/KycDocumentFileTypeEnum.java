package com.curesio.ehealth.enumerations;

public enum KycDocumentFileTypeEnum {
    PDF("PDF"),
    IMG("IMG");

    String value;

    KycDocumentFileTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
