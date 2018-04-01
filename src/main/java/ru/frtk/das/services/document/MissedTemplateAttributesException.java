package ru.frtk.das.services.document;

import java.util.Set;

import static java.lang.String.format;

public class MissedTemplateAttributesException extends RuntimeException {
    public final Set<String> missedAttributes;

    public MissedTemplateAttributesException(Set<String> requiredTemplateAttributes) {
        super(format("Missed fields for template: %s", requiredTemplateAttributes));
        this.missedAttributes = requiredTemplateAttributes;
    }
}
