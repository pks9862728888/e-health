package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.ResourceKycDocumentFileTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ResourceKycDocumentFileTypeEnumConverter implements AttributeConverter<ResourceKycDocumentFileTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(ResourceKycDocumentFileTypeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ResourceKycDocumentFileTypeEnum convertToEntityAttribute(String dbData) {
        return Stream.of(ResourceKycDocumentFileTypeEnum.values())
                .filter(t -> t.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
