package ru.frtk.das.services.document;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.frtk.das.microtypes.TemplateValue;
import ru.frtk.das.model.*;
import ru.frtk.das.utils.Result;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.frtk.das.microtypes.LocalDateValue.localDateValue;
import static ru.frtk.das.microtypes.StringValue.stringValue;
import static ru.frtk.das.model.ModelAttributeValue.modelAttributeValue;
import static ru.frtk.das.model.StandardAttributes.dateTodayAttribute;
import static ru.frtk.das.model.StandardAttributes.templateNameAttribute;
import static ru.frtk.das.services.document.DocumentPdfRender.render;
import static ru.frtk.das.utils.Result.resultOf;

@Service
public class DocumentService {
    private final TemplateRepository templateRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public DocumentService(final TemplateRepository templateRepository,
                           final UserProfileRepository userProfileRepository) {
        this.templateRepository = templateRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public Result<byte[]> generateDocument(
            final UUID userId,
            final String templateName,
            final Map<String, ModelAttributeValue<? extends TemplateValue>> additionalAttributes
    ) {
        final Template template = templateRepository.getByTemplateNameEquals(templateName);
        final UserProfile userProfile = userProfileRepository.getOne(userId);

        final Map<String, ModelAttributeValue<? extends TemplateValue>> parameters =
                ImmutableMap.<String, ModelAttributeValue<? extends TemplateValue>>builder()
                .putAll(userProfile.getAttributesValues()
                        .entrySet()
                        .stream()
                        .map(e -> Pair.of(e.getKey().getAttributeName(), e.getValue()))
                        .collect(Collectors.toMap(Pair::getKey, Pair::getValue))
                )
                .putAll(additionalAttributes)
                .put(
                        templateNameAttribute().getAttributeName(),
                        modelAttributeValue(templateNameAttribute(), stringValue(template.getTemplateName()))
                )
                .put(
                        dateTodayAttribute().getAttributeName(),
                        modelAttributeValue(dateTodayAttribute(), localDateValue(LocalDate.now()))
                )
                .build();

        return resultOf(() -> render(template, parameters));
    }
}
