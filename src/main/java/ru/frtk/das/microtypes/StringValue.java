package ru.frtk.das.microtypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snell.michael.kawaii.MicroType;

public class StringValue extends MicroType<String> implements TemplateValue {

    public static StringValue stringValue(String value) {
        return new StringValue(value);
    }

    @JsonCreator
    public StringValue(@JsonProperty("value") String value) {
        super(value);
    }

    @Override
    public TemplateValue valueOf(String value) {
        return stringValue(value);
    }

    @Override
    public String templateValue() {
        return value;
    }
}
