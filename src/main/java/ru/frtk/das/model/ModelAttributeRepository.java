package ru.frtk.das.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.frtk.das.microtypes.TemplateValue;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ModelAttributeRepository
        extends JpaRepository<ModelAttribute, UUID>, QuerydslPredicateExecutor<ModelAttribute> {

    <T extends TemplateValue> Optional<ModelAttribute<T>> findByAttributeName(String attributeName);

    boolean existsByAttributeName(String attributeName);
}
