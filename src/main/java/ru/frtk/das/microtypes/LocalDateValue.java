package ru.frtk.das.microtypes;

import com.snell.michael.kawaii.MicroType;

import java.time.LocalDate;

public class LocalDateValue extends MicroType<LocalDate> implements TemplateValue {
    public static LocalDateValue localDateValue(LocalDate localDate) {
        return new LocalDateValue(localDate);
    }

    public LocalDateValue(LocalDate value) {
        super(value);
    }

    @Override
    public TemplateValue valueOf(String string) {
        return null;
    }

    @Override
    public String templateValue() {
        return null;
    }
}
