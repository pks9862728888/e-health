package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.CurrencyEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CurrencyEnumConverter implements AttributeConverter<CurrencyEnum, String> {

    @Override
    public String convertToDatabaseColumn(CurrencyEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public CurrencyEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Stream.of(CurrencyEnum.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
