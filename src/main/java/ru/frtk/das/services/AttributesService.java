package ru.frtk.das.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.frtk.das.microtypes.TemplateValue;
import ru.frtk.das.model.ModelAttribute;
import ru.frtk.das.model.ModelAttributeRepository;
import ru.frtk.das.model.Template;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttributesService {
    private final ModelAttributeRepository modelAttributeRepo;

    @Autowired
    public AttributesService(ModelAttributeRepository modelAttributeRepo) {
        this.modelAttributeRepo = modelAttributeRepo;
    }

    public boolean isSupportedAttribute(String attributeName) {
        return modelAttributeRepo.existsByAttributeName(attributeName);
    }

    public Optional<ModelAttribute<? extends TemplateValue>> findAttribute(String attributeName) {
        return modelAttributeRepo.findByAttributeName(attributeName)
                .map(a -> a);
    }

    public Collection<ModelAttribute> allAttributesForProfile() {
        return modelAttributeRepo.findAll().stream()
                .filter(ModelAttribute::isForProfile)
                .collect(Collectors.toSet());
    }
}
