package com.curesio.ehealth.enumerations.converters;

import com.curesio.ehealth.enumerations.KycDocumentFileTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ResourceKycDocumentFileTypeEnumConverter implements AttributeConverter<KycDocumentFileTypeEnum, String> {

    @Override
    public String convertToDatabaseColumn(KycDocumentFileTypeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public KycDocumentFileTypeEnum convertToEntityAttribute(String dbData) {
        return Stream.of(KycDocumentFileTypeEnum.values())
                .filter(t -> t.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
