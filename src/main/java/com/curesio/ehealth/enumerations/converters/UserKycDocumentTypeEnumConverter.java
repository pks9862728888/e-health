package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.UserKycDocumentTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserKycDocumentTypeEnumConverter implements AttributeConverter<UserKycDocumentTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(UserKycDocumentTypeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public UserKycDocumentTypeEnum convertToEntityAttribute(String dbData) {
        return Stream.of(UserKycDocumentTypeEnum.values())
                .filter(a -> a.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
