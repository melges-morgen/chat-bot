package ru.frtk.das.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.diffplug.common.base.Errors.rethrow;

@Converter
public class AttributesValuesConverter
        implements AttributeConverter<Map<ModelAttribute<?>, ModelAttributeValue<?>>, String> {

    @Override
    public String convertToDatabaseColumn(Map<ModelAttribute<?>, ModelAttributeValue<?>> attribute) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return rethrow().get(() -> mapper.writeValueAsString(attribute.values()));
    }

    @Override
    public Map<ModelAttribute<?>, ModelAttributeValue<?>> convertToEntityAttribute(String dbData) {
        ObjectMapper mapper = new ObjectMapper();
        Set<ModelAttributeValue<?>> storedSet = rethrow().get(
                () -> mapper
                        .readValue(dbData, new TypeReference<Set<ModelAttributeValue>>(){})
        );

        return storedSet.stream()
                .collect(Collectors.toMap(e -> e.attribute, e -> e));
    }
}
