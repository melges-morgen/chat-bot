package ru.frtk.das.microtypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringValue.class),
        @JsonSubTypes.Type(value = BooleanValue.class),
        @JsonSubTypes.Type(value = LocalDateValue.class),
})
public interface TemplateValue<T> {
    TemplateValue<T> valueOf(String string);
    String templateValue();
}
