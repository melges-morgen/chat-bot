package ru.frtk.das.model;

import com.snell.michael.kawaii.MicroType;
import ru.frtk.das.microtypes.Email;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class DatabaseConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email attribute) {
        return Optional.ofNullable(attribute).map(MicroType::value).orElse(null);
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }

        return Email.email(dbData);
    }
}
