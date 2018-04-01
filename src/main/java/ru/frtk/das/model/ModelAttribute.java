package ru.frtk.das.model;

import ru.frtk.das.microtypes.TemplateValue;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "attributes")
public class ModelAttribute<T extends TemplateValue> {


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

    public static <T extends TemplateValue> ModelAttribute<T> modelAttribute(UUID id, Class<T> clazz) {
        return new ModelAttribute<T>().setId(id).setAttributeClass(clazz);
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

    @Override
    public String toString() {
        return "ModelAttribute{" +
                "id=" + id +
                ", attributeName='" + attributeName + '\'' +
                ", attributeDescription='" + attributeDescription + '\'' +
                ", attributeClass=" + attributeClass +
                '}';
    }
}
