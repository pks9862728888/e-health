package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.AccountTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AccountTypeEnumConverter implements AttributeConverter<AccountTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(AccountTypeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public AccountTypeEnum convertToEntityAttribute(String dbData) {
        return Stream.of(AccountTypeEnum.values())
                .filter(a -> a.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
