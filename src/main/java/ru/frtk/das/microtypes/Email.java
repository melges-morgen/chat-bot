package ru.frtk.das.microtypes;

import com.snell.michael.kawaii.MicroType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public class Email extends MicroType<String> {

    @Converter
    public static class DatabaseConverter implements AttributeConverter<Email, String> {

        @Override
        public String convertToDatabaseColumn(Email attribute) {
            return attribute.value;
        }

        @Override
        public Email convertToEntityAttribute(String dbData) {
            return email(dbData);
        }
    }

    public static Email email(String email) {
        return new Email(email);
    }

    public Email(String value) {
        super(value);
    }
}
