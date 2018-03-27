package ru.frtk.das.services.document;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.frtk.das.model.Template;
import ru.frtk.das.model.TemplateRepository;
import ru.frtk.das.model.UserProfile;
import ru.frtk.das.model.UserProfileRepository;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.frtk.das.data.TemplateData.aTemplate;
import static ru.frtk.das.data.UserProfileData.aUserProfile;

public class DocumentServiceTest {
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
        Resource resource = new ClassPathResource("templates/application-for-material-assistance.tmpl.mustache");

        Template template = aTemplate(IOUtils.toString(resource.getInputStream(), "UTF-8"));
        UserProfile userProfile = aUserProfile();

        when(templateRepository.getByTemplateNameEquals(template.getTemplateName())).thenReturn(template);
        when(userProfileRepository.getOne(userProfile.getId())).thenReturn(userProfile);

        IOUtils.write(
                sut.generateDocument(userProfile.getId(), template.getTemplateName()),
                new FileOutputStream("output.pdf")
        );

    }


}