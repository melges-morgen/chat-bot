package ru.frtk.das.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface TemplateRepository extends JpaRepository<Template, UUID>, QuerydslPredicateExecutor<Template> {
    Template getByTemplateNameEquals(String templateName);
}
