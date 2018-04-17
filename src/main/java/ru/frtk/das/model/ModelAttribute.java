package ru.frtk.das.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import ru.frtk.das.microtypes.TemplateValue;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "attributes")
public class ModelAttribute<T extends TemplateValue> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "attribute_name", unique = true, nullable = false, updatable = false)
    private String attributeName;

    @Column(name = "attribute_description")
    private String attributeDescription;

    @Column(name = "attribute_class")
    @Convert(converter = ClassConverter.class)
    private Class attributeClass;

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

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

    public boolean isHidden() {
        return hidden;
    }

    @JsonIgnore
    public boolean isVisible() {
        return !hidden;
    }

    public ModelAttribute<T> setHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelAttribute<?> that = (ModelAttribute<?>) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(attributeName, that.attributeName) &&
                Objects.equals(attributeDescription, that.attributeDescription) &&
                Objects.equals(attributeClass, that.attributeClass) &&
                Objects.equals(hidden, that.hidden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attributeName, attributeDescription, attributeClass, hidden);
    }

    @Override
    public String toString() {
        return "ModelAttribute{" +
                "id=" + id +
                ", attributeName='" + attributeName + '\'' +
                ", attributeDescription='" + attributeDescription + '\'' +
                ", attributeClass=" + attributeClass +
                ", hidden=" + hidden +
                '}';
    }
}
