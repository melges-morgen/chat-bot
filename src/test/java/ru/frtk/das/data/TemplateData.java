package ru.frtk.das.data;

import ru.frtk.das.model.Template;

import java.util.UUID;

public interface TemplateData {
    static Template aTemplate() {
        return new Template()
                .setTemplateName("aTemplate")
                .setId(UUID.nameUUIDFromBytes("aTemplate".getBytes()));
    }

    static Template aTemplate(String text) {
        return new Template()
                .setTemplateName("aTemplate")
                .setId(UUID.nameUUIDFromBytes("aTemplate".getBytes()))
                .setTemplateText(text);
    }
}
