package ru.frtk.das.microtypes;

public interface TemplateValue<T> {
    TemplateValue<T> valueOf(String string);
    String templateValue();
}
