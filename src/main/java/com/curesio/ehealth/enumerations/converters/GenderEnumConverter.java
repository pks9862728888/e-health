package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.GenderEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class GenderEnumConverter implements AttributeConverter<GenderEnum, Character> {

    @Override
    public Character convertToDatabaseColumn(GenderEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public GenderEnum convertToEntityAttribute(Character dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(GenderEnum.values())
                .filter(e -> e.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
