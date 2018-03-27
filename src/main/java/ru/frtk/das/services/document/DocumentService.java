package ru.frtk.das.services.document;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.frtk.das.microtypes.TemplateValue;
import ru.frtk.das.model.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.frtk.das.services.document.DocumentPdfRender.render;

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

    public byte[] generateDocument(final UUID userId, final String templateName) {
        final Template template = templateRepository.getByTemplateNameEquals(templateName);
        final UserProfile userProfile = userProfileRepository.getOne(userId);

        final Map<String, ModelAttributeValue<? extends TemplateValue>> parameters =
                userProfile.getAttributesValues()
                        .entrySet()
                        .stream()
                        .map(e -> Pair.of(e.getKey().getAttributeName(), e.getValue()))
                        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        return render(template, parameters);
    }
}
