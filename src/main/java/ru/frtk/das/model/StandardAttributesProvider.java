package ru.frtk.das.model;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;
import ru.frtk.das.microtypes.BooleanValue;
import ru.frtk.das.microtypes.LocalDateValue;
import ru.frtk.das.microtypes.StringValue;
import ru.frtk.das.microtypes.TemplateValue;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.nameUUIDFromBytes;

@Component
public class StandardAttributesProvider {
    private static final UUID NAME_ID = nameUUIDFromBytes("name".getBytes());
    private static final UUID NAME_GEN_ID = nameUUIDFromBytes("name_gen".getBytes());
    private static final UUID SURNAME_ID = nameUUIDFromBytes("surname".getBytes());
    private static final UUID SURNAME_GEN_ID = nameUUIDFromBytes("surname_gen".getBytes());
    private static final UUID BIRTH_DATE_ID = nameUUIDFromBytes("birth_date".getBytes());
    private static final UUID TEMPLATE_NAME_ID = nameUUIDFromBytes("template_name".getBytes());
    private static final UUID IS_MAIL_ATTRIBUTE_ID = nameUUIDFromBytes("is_male".getBytes());
    private static final UUID DATE_TODAY_ID = nameUUIDFromBytes("date_today".getBytes());

    private final ModelAttributeRepository modelAttributeRepo;
    private final Set<UUID> attributesIds = ImmutableSet
            .<UUID>builder()
            .add(NAME_ID)
            .add(NAME_GEN_ID)
            .add(SURNAME_ID)
            .add(SURNAME_GEN_ID)
            .add(BIRTH_DATE_ID)
            .add(TEMPLATE_NAME_ID)
            .add(IS_MAIL_ATTRIBUTE_ID)
            .add(DATE_TODAY_ID)
            .build();

    public Collection<ModelAttribute<? extends TemplateValue<?>>> attributes() {
        return modelAttributeRepo.attributesById(attributesIds);
    }

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

    public boolean isStandardByName(String attributeName) {
        return attributesIds.contains(nameUUIDFromBytes(attributeName.getBytes()));
    }
}
