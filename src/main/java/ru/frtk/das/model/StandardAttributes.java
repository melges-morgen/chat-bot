package ru.frtk.das.model;

import ru.frtk.das.microtypes.BooleanValue;
import ru.frtk.das.microtypes.LocalDateValue;
import ru.frtk.das.microtypes.StringValue;

import static java.util.UUID.nameUUIDFromBytes;
import static ru.frtk.das.model.ModelAttribute.modelAttribute;

public class StandardAttributes {
    private final static ModelAttribute<StringValue> NAME =
            modelAttribute(nameUUIDFromBytes("name".getBytes()), StringValue.class)
                    .setAttributeName("name");

    private final static ModelAttribute<StringValue> SURNAME =
            modelAttribute(nameUUIDFromBytes("surname".getBytes()), StringValue.class)
                    .setAttributeName("surname");

    private final static ModelAttribute<LocalDateValue> BIRTH_DATE =
            modelAttribute(nameUUIDFromBytes("birth_date".getBytes()), LocalDateValue.class)
                    .setAttributeName("birth_date");
    private static final ModelAttribute<StringValue> TEMPLATE_NAME =
            modelAttribute(nameUUIDFromBytes("template_name".getBytes()), StringValue.class)
                    .setAttributeName("template_name");
    private static final ModelAttribute<BooleanValue> IS_MAIL_ATTRIBUTE =
            modelAttribute(nameUUIDFromBytes("is_male".getBytes()), BooleanValue.class)
                    .setAttributeName("is_male");
    private static final ModelAttribute<LocalDateValue> DATE_TODAY_ATTRIBUTE =
            modelAttribute(nameUUIDFromBytes("date_today".getBytes()), LocalDateValue.class)
            .setAttributeName("date_today");

    public static ModelAttribute<StringValue> nameAttribute() {
        return NAME;
    }

    public static ModelAttribute<StringValue> surnameAttribute() {
        return SURNAME;
    }

    public static ModelAttribute<LocalDateValue> birthDateAttribute() {
        return BIRTH_DATE;
    }

    public static ModelAttribute<StringValue> templateNameAttribute() {
        return TEMPLATE_NAME;
    }

    public static ModelAttribute<BooleanValue> isMaleAttribute() {
        return IS_MAIL_ATTRIBUTE;
    }

    public static ModelAttribute<LocalDateValue> dateTodayAttribute() {
        return DATE_TODAY_ATTRIBUTE;
    }
}
