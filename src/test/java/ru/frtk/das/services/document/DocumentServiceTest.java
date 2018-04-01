package ru.frtk.das.services.document;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.frtk.das.microtypes.StringValue;
import ru.frtk.das.model.*;

import java.io.IOException;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static ru.frtk.das.data.TemplateData.aTemplate;
import static ru.frtk.das.data.UserProfileData.aUserProfile;
import static ru.frtk.das.microtypes.StringValue.stringValue;
import static ru.frtk.das.model.ModelAttribute.modelAttribute;
import static ru.frtk.das.model.ModelAttributeValue.modelAttributeValue;

public class DocumentServiceTest {
    private static final ModelAttribute<StringValue> reasonAttribute =
            modelAttribute(randomUUID(), StringValue.class).setAttributeName("reason");
    private static final ModelAttribute<StringValue> groupAttribute =
            modelAttribute(randomUUID(), StringValue.class).setAttributeName("group");

    private DocumentService sut;
    private TemplateRepository templateRepository;
    private UserProfileRepository userProfileRepository;

    @Before
    public void setUp() {
        templateRepository = mock(TemplateRepository.class);
        userProfileRepository = mock(UserProfileRepository.class);
        sut = new DocumentService(templateRepository, userProfileRepository);
    }

    @Test
    public void should_generate_pdf_file() throws IOException {
        Resource templateSource = new ClassPathResource("templates/application-for-material-assistance.tmpl.mustache");

        Template template = aTemplate(IOUtils.toString(templateSource.getInputStream(), "UTF-8"));
        UserProfile userProfile = aUserProfile();

        when(templateRepository.getByTemplateNameEquals(template.getTemplateName())).thenReturn(template);
        when(userProfileRepository.getOne(userProfile.getId())).thenReturn(userProfile);

        //when
        byte[] output = sut.generateDocument(
                userProfile.getId(),
                template.getTemplateName(),
                ImmutableMap.of(
                        "reason", modelAttributeValue(reasonAttribute, stringValue("Test reason")),
                        "group", modelAttributeValue(groupAttribute, stringValue("213")),
                        "date", modelAttributeValue(groupAttribute, stringValue("01.02.2018"))
                )
        ).orThrow();

        // then
        assertTrue("Output is not empty", output.length > 100);
    }

    @Test
    public void should_return_error_with_missed_attributes() throws IOException {
        Resource templateSource = new ClassPathResource("templates/application-for-material-assistance.tmpl.mustache");

        Template template = aTemplate(IOUtils.toString(templateSource.getInputStream(), "UTF-8"));
        UserProfile userProfile = aUserProfile();

        when(templateRepository.getByTemplateNameEquals(template.getTemplateName())).thenReturn(template);
        when(userProfileRepository.getOne(userProfile.getId())).thenReturn(userProfile);

        // then
        assertThatThrownBy(() -> sut.generateDocument(
                userProfile.getId(),
                template.getTemplateName(),
                ImmutableMap.of(
                        "reason", modelAttributeValue(reasonAttribute, stringValue("Test reason")),
                        "group", modelAttributeValue(groupAttribute, stringValue("213"))
                )
        ).orThrow())
                .hasCauseInstanceOf(MissedTemplateAttributesException.class)
                .hasCause(new MissedTemplateAttributesException(ImmutableSet.of("date")));
    }
}