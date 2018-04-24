package ru.frtk.das.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.frtk.das.microtypes.TemplateValue;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ModelAttributeRepository
        extends JpaRepository<ModelAttribute, UUID>, QuerydslPredicateExecutor<ModelAttribute> {

    <T extends TemplateValue> Optional<ModelAttribute<T>> findByAttributeName(String attributeName);

    boolean existsByAttributeName(String attributeName);

    @Query("SELECT a FROM ModelAttribute a WHERE a.attributeName in :names")
    Collection<ModelAttribute<? extends TemplateValue<?>>> attributesByName(@Param("names") String... names);

    @Query("SELECT a FROM ModelAttribute a WHERE a.id in :ids")
    Collection<ModelAttribute<? extends TemplateValue<?>>> attributesById(@Param("ids") Collection<UUID> ids);
}
