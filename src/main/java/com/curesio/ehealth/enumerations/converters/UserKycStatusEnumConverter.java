package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.UserKycStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserKycStatusEnumConverter implements AttributeConverter<UserKycStatusEnum, Character> {

    @Override
    public Character convertToDatabaseColumn(UserKycStatusEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public UserKycStatusEnum convertToEntityAttribute(Character dbData) {
        return Stream.of(UserKycStatusEnum.values())
                .filter(u -> u.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
