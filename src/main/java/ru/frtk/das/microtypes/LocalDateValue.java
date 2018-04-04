package ru.frtk.das.microtypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snell.michael.kawaii.MicroType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateValue extends MicroType<LocalDate> implements TemplateValue {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDateValue localDateValue(LocalDate localDate) {
        return new LocalDateValue(localDate);
    }

    @JsonCreator
    public LocalDateValue(@JsonProperty("value") LocalDate value) {
        super(value);
    }

    @Override
    public TemplateValue valueOf(String string) {
        return localDateValue(LocalDate.from(formatter.parse(string)));
    }

    @Override
    public String templateValue() {
        return formatter.format(value);
    }
}
