package ru.frtk.das.model;

import com.google.common.collect.ImmutableMap;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.frtk.das.microtypes.TemplateValue;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ru.frtk.das.microtypes.LocalDateValue.localDateValue;
import static ru.frtk.das.microtypes.StringValue.stringValue;
import static ru.frtk.das.model.ModelAttribute.*;
import static ru.frtk.das.model.ModelAttributeValue.modelAttributeValue;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(name = "id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "avatar_uri")
    private URI avatar;

    @Type(type = "jsonb")
    @Column(name = "attributes_values", columnDefinition = "clob")
    private Map<ModelAttribute<?>, ModelAttributeValue<?>> attributesValues = new HashMap<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Map<ModelAttribute<? extends TemplateValue>, ModelAttributeValue<? extends TemplateValue>>
    getAttributesValues() {
        return ImmutableMap.<ModelAttribute<?>, ModelAttributeValue<?>>builder()
                .putAll(attributesValues)
                .put(nameAttribute(), modelAttributeValue(nameAttribute(), stringValue(name)))
                .put(surnameAttribute(), modelAttributeValue(surnameAttribute(), stringValue(surname)))
                .put(birthDateAttribute(), modelAttributeValue(birthDateAttribute(), localDateValue(birthDate)))
                .build();
    }

    public void setAttributeValue(ModelAttribute<?> attribute, ModelAttributeValue<?> attributeValue) {
        this.attributesValues.put(attribute, attributeValue);
    }

    public URI getAvatar() {
        return avatar;
    }

    public void setAvatar(URI avatar) {
        this.avatar = avatar;
    }
}
