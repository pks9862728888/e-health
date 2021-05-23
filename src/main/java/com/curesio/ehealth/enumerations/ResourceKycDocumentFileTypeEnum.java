package com.curesio.ehealth.enumerations;

public enum ResourceKycDocumentFileTypeEnum {
    PDF("PDF"),
    IMG("IMG");

    String value;

    ResourceKycDocumentFileTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
