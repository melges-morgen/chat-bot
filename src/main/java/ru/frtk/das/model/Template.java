package ru.frtk.das.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "template_name", unique = true, nullable = false)
    private String templateName;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="templates_attributes",
            joinColumns=@JoinColumn(name="template_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="attribute_id", referencedColumnName="id"))
    private List<ModelAttribute> attributes;

    @Lob
    @Column(name = "template_text")
    private String templateText;

    public UUID getId() {
        return id;
    }

    public Template setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Template setTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Template setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<ModelAttribute> getAttributes() {
        return attributes;
    }

    public Template setAttributes(List<ModelAttribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public String getTemplateText() {
        return templateText;
    }

    public Template setTemplateText(String templateText) {
        this.templateText = templateText;
        return this;
    }
}
