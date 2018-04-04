package ru.frtk.das.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static com.diffplug.common.base.Errors.rethrow;

@Converter(autoApply = true)
public class ClassConverter implements AttributeConverter<Class, String> {

    @Override
    public String convertToDatabaseColumn(Class attribute) {
        return attribute.getName();
    }

    @Override
    public Class convertToEntityAttribute(String dbData) {
        return rethrow().get(() -> Class.forName(dbData));
    }
}
