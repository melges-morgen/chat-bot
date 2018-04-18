package ru.frtk.das.model;

import org.springframework.stereotype.Component;
import ru.frtk.das.microtypes.BooleanValue;
import ru.frtk.das.microtypes.LocalDateValue;
import ru.frtk.das.microtypes.StringValue;

import java.util.UUID;

import static java.util.UUID.nameUUIDFromBytes;

@Component
public class StandardAttributesProvider {
    private final static UUID NAME_ID = nameUUIDFromBytes("name".getBytes());
    private final static UUID NAME_GEN_ID = nameUUIDFromBytes("name_gen".getBytes());
    private final static UUID SURNAME_ID = nameUUIDFromBytes("surname".getBytes());
    private final static UUID SURNAME_GEN_ID = nameUUIDFromBytes("surname_gen".getBytes());
    private final static UUID BIRTH_DATE_ID = nameUUIDFromBytes("birth_date".getBytes());
    private static final UUID TEMPLATE_NAME_ID = nameUUIDFromBytes("template_name".getBytes());
    private static final UUID IS_MAIL_ATTRIBUTE_ID = nameUUIDFromBytes("is_male".getBytes());
    private static final UUID DATE_TODAY_ID = nameUUIDFromBytes("date_today".getBytes());

    private final ModelAttributeRepository modelAttributeRepo;


    public StandardAttributesProvider(ModelAttributeRepository modelAttributeRepo) {
        this.modelAttributeRepo = modelAttributeRepo;
    }

    public ModelAttribute<StringValue> nameAttribute() {
        return modelAttributeRepo.getOne(NAME_ID);
    }

    public ModelAttribute<StringValue> nameGenAttribute() {
        return modelAttributeRepo.getOne(NAME_GEN_ID);
    }

    public ModelAttribute<StringValue> surnameAttribute() {
        return modelAttributeRepo.getOne(SURNAME_ID);
    }

    public ModelAttribute<StringValue> surnameGenAttribute() {
        return modelAttributeRepo.getOne(SURNAME_GEN_ID);
    }

    public ModelAttribute<LocalDateValue> birthDateAttribute() {
        return modelAttributeRepo.getOne(BIRTH_DATE_ID);
    }

    public ModelAttribute<StringValue> templateNameAttribute() {
        return modelAttributeRepo.getOne(TEMPLATE_NAME_ID);
    }

    public ModelAttribute<BooleanValue> isMaleAttribute() {
        return modelAttributeRepo.getOne(IS_MAIL_ATTRIBUTE_ID);
    }

    public ModelAttribute<LocalDateValue> dateTodayAttribute() {
        return modelAttributeRepo.getOne(DATE_TODAY_ID);
    }
}
