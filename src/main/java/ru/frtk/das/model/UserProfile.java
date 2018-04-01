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

import static ru.frtk.das.microtypes.BooleanValue.booleanValue;
import static ru.frtk.das.microtypes.LocalDateValue.localDateValue;
import static ru.frtk.das.microtypes.StringValue.stringValue;
import static ru.frtk.das.model.ModelAttributeValue.modelAttributeValue;
import static ru.frtk.das.model.StandardAttributes.*;
import static ru.frtk.das.model.UserProfile.Gender.MALE;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "user_profile")
public class UserProfile {
    enum Gender {
        MALE,
        FEMALE
    }

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

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Type(type = "jsonb")
    @Column(name = "attributes_values", columnDefinition = "clob")
    private Map<ModelAttribute<?>, ModelAttributeValue<?>> attributesValues = new HashMap<>();

    public UUID getId() {
        return id;
    }

    public UserProfile setId(UUID id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserProfile setUser(User user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserProfile setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserProfile setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UserProfile setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Map<ModelAttribute<? extends TemplateValue>,
            ModelAttributeValue<? extends TemplateValue>> getAttributesValues() {
        return ImmutableMap.<ModelAttribute<?>, ModelAttributeValue<?>>builder()
                .putAll(attributesValues)
                .put(nameAttribute(), modelAttributeValue(nameAttribute(), stringValue(name)))
                .put(surnameAttribute(), modelAttributeValue(surnameAttribute(), stringValue(surname)))
                .put(birthDateAttribute(), modelAttributeValue(birthDateAttribute(), localDateValue(birthDate)))
                .put(isMaleAttribute(), modelAttributeValue(isMaleAttribute(), booleanValue(gender == MALE)))
                .build();
    }

    public UserProfile setAttributeValue(ModelAttribute<?> attribute, ModelAttributeValue<?> attributeValue) {
        this.attributesValues.put(attribute, attributeValue);
        return this;
    }

    public URI getAvatar() {
        return avatar;
    }

    public UserProfile setAvatar(URI avatar) {
        this.avatar = avatar;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
