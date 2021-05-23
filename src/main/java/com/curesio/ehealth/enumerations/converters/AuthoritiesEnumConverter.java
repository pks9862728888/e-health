package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.AuthoritiesEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AuthoritiesEnumConverter implements AttributeConverter<AuthoritiesEnum, String> {
    @Override
    public String convertToDatabaseColumn(AuthoritiesEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public AuthoritiesEnum convertToEntityAttribute(String dbData) {
        return Stream.of(AuthoritiesEnum.values())
                .filter(a -> a.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
