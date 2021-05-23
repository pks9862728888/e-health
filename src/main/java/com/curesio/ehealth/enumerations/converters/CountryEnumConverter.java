package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.CountryEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CountryEnumConverter implements AttributeConverter<CountryEnum, String> {

    @Override
    public String convertToDatabaseColumn(CountryEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public CountryEnum convertToEntityAttribute(String dbData) {
        return Stream.of(CountryEnum.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
