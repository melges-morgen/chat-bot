package ru.frtk.das.microtypes;

import com.snell.michael.kawaii.MicroType;

public class BooleanValue extends MicroType<Boolean> implements TemplateValue<Boolean> {
    public static BooleanValue booleanValue(boolean value) {
        return new BooleanValue(value);
    }

    public BooleanValue(Boolean value) {
        super(value);
    }

    @Override
    public TemplateValue<Boolean> valueOf(String string) {
        return booleanValue(Boolean.valueOf(string));
    }

    @Override
    public String templateValue() {
        return String.valueOf(value);
    }
}
