package ru.frtk.das.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "attributes")
public class ModelAttribute {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "attribute_name", unique = true)
    private String attributeName;

    @Column(name = "attribute_description")
    private String attributeDescription;

    @Column(name = "class")
    private Class attributeClass;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeDescription() {
        return attributeDescription;
    }

    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }

    public Class getAttributeClass() {
        return attributeClass;
    }

    public void setAttributeClass(Class attributeClass) {
        this.attributeClass = attributeClass;
    }
}
