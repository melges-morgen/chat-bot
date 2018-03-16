package ru.frtk.das.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "attributes")
public class ModelAttribute<T> {
    private final static ModelAttribute<String> NAME =
            modelAttribute(UUID.fromString("name"), String.class).setAttributeName("name");
    private final static ModelAttribute<String> SURNAME =
            modelAttribute(UUID.fromString("surname"), String.class).setAttributeName("surname");
    private final static ModelAttribute<LocalDate> BIRTH_DATE =
            modelAttribute(UUID.fromString("birth_date"), LocalDate.class).setAttributeName("birth_date");

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "attribute_name", unique = true, nullable = false, updatable = false)
    private String attributeName;

    @Column(name = "attribute_description")
    private String attributeDescription;

    @Column(name = "class", nullable = false)
    private Class<T> attributeClass;

    public static <T> ModelAttribute<T> modelAttribute(UUID id, Class<T> clazz) {
        return new ModelAttribute<T>().setAttributeClass(clazz);
    }

    public static ModelAttribute<String> nameAttribute() {
        return NAME;
    }

    public static ModelAttribute<String> surnameAttribute() {
        return SURNAME;
    }

    public static ModelAttribute<LocalDate> birthDateAttribute() {
        return BIRTH_DATE;
    }

    public UUID getId() {
        return id;
    }

    public ModelAttribute<T> setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public ModelAttribute<T> setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public String getAttributeDescription() {
        return attributeDescription;
    }

    public ModelAttribute<T> setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
        return this;
    }

    public Class<T> getAttributeClass() {
        return attributeClass;
    }

    public ModelAttribute<T> setAttributeClass(Class<T> attributeClass) {
        this.attributeClass = attributeClass;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelAttribute<?> that = (ModelAttribute<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
