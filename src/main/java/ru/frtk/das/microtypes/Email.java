package ru.frtk.das.microtypes;

import com.snell.michael.kawaii.MicroType;

public class Email extends MicroType<String> {

    public static Email email(String email) {
        return new Email(email);
    }

    public Email(String value) {
        super(value);
    }
}
