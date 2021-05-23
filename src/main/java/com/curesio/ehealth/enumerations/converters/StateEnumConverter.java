package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.StateEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StateEnumConverter implements AttributeConverter<StateEnum, String> {

    @Override
    public String convertToDatabaseColumn(StateEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public StateEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(StateEnum.values())
                .filter(s -> s.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
