package ru.frtk.das.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.frtk.das.microtypes.TemplateValue;

import java.util.Objects;

public class ModelAttributeValue<T extends TemplateValue> {
    public final ModelAttribute<T> attribute;
    public final T value;

    public static <T extends TemplateValue> ModelAttributeValue<T> modelAttributeValue(ModelAttribute<T> attribute,
                                                                                       T value) {
        return new ModelAttributeValue<>(attribute, value);
    }

    @JsonCreator
    public ModelAttributeValue(
            @JsonProperty("attribute") ModelAttribute<T> attribute,
            @JsonProperty("value") T value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelAttributeValue<?> that = (ModelAttributeValue<?>) o;
        return Objects.equals(attribute, that.attribute) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, value);
    }

    @Override
    public String toString() {
        return value.templateValue();
    }
}
