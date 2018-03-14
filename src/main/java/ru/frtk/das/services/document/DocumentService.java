package ru.frtk.das.services.document;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.frtk.das.model.Template;
import ru.frtk.das.model.TemplateRepository;
import ru.frtk.das.model.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.vladsch.flexmark.pdf.converter.PdfConverterExtension.exportToPdf;

@Service
public class DocumentService {
    private static final DataHolder options = PegdownOptionsAdapter.flexmarkOptions(Extensions.ALL);
    private static final Parser parser =  Parser.builder(options).build();
    private static final HtmlRenderer htmlRenderer = HtmlRenderer.builder(options).build();

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    @Autowired
    public DocumentService(TemplateRepository templateRepository, UserRepository userRepository) {
        this.templateRepository = templateRepository;
        this.userRepository = userRepository;
    }

    public byte[] generateDcoument(UUID userId, String templateName) {
        Template template = templateRepository.getByTemplateNameEquals(templateName);
        try(ByteArrayOutputStream resultStream = new ByteArrayOutputStream()) {
            Mustache documentSource = new DefaultMustacheFactory().compile(template.getTemplateText());
//            documentSource.getCodes()
//            documentSource.
            Node document = parser.parse(template.getTemplateText());
            exportToPdf(resultStream, htmlRenderer.render(document), "", options);
            return resultStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to generate document");
        }
    }


}
