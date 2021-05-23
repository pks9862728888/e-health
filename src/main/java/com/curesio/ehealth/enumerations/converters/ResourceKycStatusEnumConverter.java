package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.ResourceKycStatusEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ResourceKycStatusEnumConverter implements AttributeConverter<ResourceKycStatusEnum, Character> {

    @Override
    public Character convertToDatabaseColumn(ResourceKycStatusEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ResourceKycStatusEnum convertToEntityAttribute(Character dbData) {
        return Stream.of(ResourceKycStatusEnum.values())
                .filter(a -> a.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
