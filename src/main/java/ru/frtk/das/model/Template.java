package ru.frtk.das.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ModelAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ModelAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }
}
