package ru.frtk.das.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.frtk.das.microtypes.TemplateValue;
import ru.frtk.das.model.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttributesService {
    private final UserProfileRepository userProfileRepo;
    private final ModelAttributeRepository modelAttributeRepo;
    private final StandardAttributesProvider standardAttributesProvider;

    @Autowired
    public AttributesService(UserProfileRepository userProfileRepo,
                             ModelAttributeRepository modelAttributeRepo,
                             StandardAttributesProvider standardAttributesProvider) {
        this.userProfileRepo = userProfileRepo;
        this.modelAttributeRepo = modelAttributeRepo;
        this.standardAttributesProvider = standardAttributesProvider;
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
                .filter(ModelAttribute::isVisible)
                .collect(Collectors.toSet());
    }

    public Optional<ModelAttributeValue> attributeValue(String attributeName, User user) {
        if (standardAttributesProvider.isStandardByName(attributeName)) {

        }
        return userProfileRepo.getUserProfileByUser(user). ;
    }
}
