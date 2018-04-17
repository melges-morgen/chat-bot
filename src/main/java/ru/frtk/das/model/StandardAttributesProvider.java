package ru.frtk.das.model;

import org.springframework.stereotype.Component;
import ru.frtk.das.microtypes.BooleanValue;
import ru.frtk.das.microtypes.LocalDateValue;
import ru.frtk.das.microtypes.StringValue;

import static java.util.UUID.nameUUIDFromBytes;
import static ru.frtk.das.model.ModelAttribute.modelAttribute;

@Component
public class StandardAttributesProvider {
    private

    private final static ModelAttribute<StringValue> NAME_ID =
            modelAttribute(nameUUIDFromBytes("name".getBytes()), StringValue.class)
                    .setAttributeName("name");
    private final static ModelAttribute<StringValue> NAME_GEN_ID =
            modelAttribute(nameUUIDFromBytes("name_gen".getBytes()), StringValue.class)
                    .setAttributeName("name_gen")
                    .setHidden(true);

    private final static ModelAttribute<StringValue> SURNAME_ID =
            modelAttribute(nameUUIDFromBytes("surname".getBytes()), StringValue.class)
                    .setAttributeName("surname");
    private final static ModelAttribute<StringValue> SURNAME_GEN_ID =
            modelAttribute(nameUUIDFromBytes("surname_gen".getBytes()), StringValue.class)
                    .setAttributeName("surname_gen")
                    .setHidden(true);

    private final static ModelAttribute<LocalDateValue> BIRTH_DATE_ID =
            modelAttribute(nameUUIDFromBytes("birth_date".getBytes()), LocalDateValue.class)
                    .setAttributeName("birth_date");

    private static final ModelAttribute<StringValue> TEMPLATE_NAME_ID =
            modelAttribute(nameUUIDFromBytes("template_name".getBytes()), StringValue.class)
                    .setAttributeName("template_name")
                    .setHidden(true);

    private static final ModelAttribute<BooleanValue> IS_MAIL_ATTRIBUTE_ID =
            modelAttribute(nameUUIDFromBytes("is_male".getBytes()), BooleanValue.class)
                    .setAttributeName("is_male")
                    .setHidden(true);

    private static final ModelAttribute<LocalDateValue> DATE_TODAY_ID =
            modelAttribute(nameUUIDFromBytes("date_today".getBytes()), LocalDateValue.class)
                    .setAttributeName("date_today")
                    .setHidden(true);

    public ModelAttribute<StringValue> nameAttribute() {
        return NAME_ID;
    }

    public ModelAttribute<StringValue> nameGenAttribute() {
        return NAME_GEN_ID;
    }

    public ModelAttribute<StringValue> surnameAttribute() {
        return SURNAME_ID;
    }

    public ModelAttribute<StringValue> surnameGenAttribute() {
        return SURNAME_GEN_ID;
    }

    public ModelAttribute<LocalDateValue> birthDateAttribute() {
        return BIRTH_DATE_ID;
    }

    public ModelAttribute<StringValue> templateNameAttribute() {
        return TEMPLATE_NAME_ID;
    }

    public ModelAttribute<BooleanValue> isMaleAttribute() {
        return IS_MAIL_ATTRIBUTE_ID;
    }

    public ModelAttribute<LocalDateValue> dateTodayAttribute() {
        return DATE_TODAY_ID;
    }
}
