package com.curesio.ehealth.enumerations;

public enum StateEnum {
    ANDAMAN_AND_NICOBAR_ISLANDS("AN", CountryEnum.INDIA),
    ANDHRA_PRADESH("AP", CountryEnum.INDIA),
    ARUNACHAL_PRADESH("AR", CountryEnum.INDIA),
    ASSAM("AS", CountryEnum.INDIA),
    BIHAR("BR", CountryEnum.INDIA),
    CHANDIGARH("CH", CountryEnum.INDIA),
    CHHATTISHGARH("CT", CountryEnum.INDIA),
    DADRA_AND_NAGAR_HAVELI("DN", CountryEnum.INDIA),
    DAMAN_AND_DIU("DD", CountryEnum.INDIA),
    DELHI("DL", CountryEnum.INDIA),
    GOA("GA", CountryEnum.INDIA),
    GUJARAT("GJ", CountryEnum.INDIA),
    HARYANA("HR", CountryEnum.INDIA),
    HIMACHAL_PRADESH("HP", CountryEnum.INDIA),
    JAMMU_AND_KASHMIR("JK", CountryEnum.INDIA),
    JHARKHAND("JH", CountryEnum.INDIA),
    KARNATAKA("KA", CountryEnum.INDIA),
    KERALA("KL", CountryEnum.INDIA),
    LAKSHADWEEP("LD", CountryEnum.INDIA),
    MADHYA_PRADESH("MP", CountryEnum.INDIA),
    MAHARASHTRA("MH", CountryEnum.INDIA),
    MANIPUR("MN", CountryEnum.INDIA),
    MEGHALAYA("ML", CountryEnum.INDIA),
    MIZORAM("MZ", CountryEnum.INDIA),
    NAGALAND("NL", CountryEnum.INDIA),
    ODISHA("OR", CountryEnum.INDIA),
    PUDUCHERRY("PY", CountryEnum.INDIA),
    PUNJAB("PB", CountryEnum.INDIA),
    RAJASTHAN("RJ", CountryEnum.INDIA),
    SHIKKIM("SK", CountryEnum.INDIA),
    TAMIL_NADU("TN", CountryEnum.INDIA),
    TELANGANA("TG", CountryEnum.INDIA),
    TRIPURA("TR", CountryEnum.INDIA),
    UTTAR_PRADESH("UP", CountryEnum.INDIA),
    UTTARAKHAND("UT", CountryEnum.INDIA),
    WEST_BENGAL("WB", CountryEnum.INDIA);

    String value;
    CountryEnum country;

    StateEnum(String value, CountryEnum country) {
        this.value = value;
        this.country = country;
    }

    public String getValue() {
        return value;
    }

    public CountryEnum getCountry() {
        return country;
    }
}