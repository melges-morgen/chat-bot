package ru.frtk.das.services.document;

import com.github.mustachejava.Code;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ClassPathResource;
import ru.frtk.das.microtypes.TemplateValue;
import ru.frtk.das.model.ModelAttributeValue;
import ru.frtk.das.model.Template;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.diffplug.common.base.Errors.rethrow;
import static com.openhtmltopdf.DOMBuilder.jsoup2DOM;
import static java.lang.String.format;

public class DocumentPdfRender {

    public static byte[] render(final Template template,
                                final Map<String, ModelAttributeValue<? extends TemplateValue>> parameters) {
        try(ByteArrayOutputStream resultStream = new ByteArrayOutputStream()) {
            final Mustache documentSource = new DefaultMustacheFactory().compile(
                    new StringReader(template.getTemplateText()),
                    template.getTemplateName()
            );

            // Our model "attribute name" is the same as "code" in template
            final Set<String> templateAttributesNames = Arrays.stream(documentSource.getCodes())
                    .map(Code::getName)
                    .collect(Collectors.toSet());

            final Map<String, ModelAttributeValue<? extends TemplateValue>> filteredParameters = parameters
                    .entrySet()
                    .stream()
                    .filter(e -> templateAttributesNames.contains(e.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            final Writer htmlResultWriter = new StringWriter(template.getTemplateText().length());

            PdfRendererBuilder pdfRendererBuilder =
                    pdfRendererBuilder(documentSource.execute(htmlResultWriter, filteredParameters));
            pdfRendererBuilder.toStream(resultStream);
            rethrow().wrap(pdfRendererBuilder::run).run();

            return resultStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to generate document", e);
        }
    }

    private static PdfRendererBuilder pdfRendererBuilder(Writer htmlWriter) {
        Document document = Jsoup.parse(htmlWriter.toString());
        PdfRendererBuilder pdfRendererBuilder = new PdfRendererBuilder();
        pdfRendererBuilder.withW3cDocument(jsoup2DOM(document), format("pdf-generated-%s.pdf", LocalDate.now()));
        pdfRendererBuilder.useFont(
                rethrow().wrap(new ClassPathResource("fonts/PT_Sans-Web-Regular.ttf")::getInputStream)::get,
                "PT Sans"
        );

        return pdfRendererBuilder;
    }

    private void checkParameters(Set<String> required) {

    }
}
