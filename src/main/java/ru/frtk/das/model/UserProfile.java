package ru.frtk.das.model;

import com.google.common.collect.ImmutableMap;
import org.hibernate.annotations.GenericGenerator;
import ru.frtk.das.microtypes.TemplateValue;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static ru.frtk.das.microtypes.BooleanValue.booleanValue;
import static ru.frtk.das.microtypes.LocalDateValue.localDateValue;
import static ru.frtk.das.microtypes.StringValue.stringValue;
import static ru.frtk.das.model.ModelAttributeValue.modelAttributeValue;
import static ru.frtk.das.model.StandardAttributes.*;
import static ru.frtk.das.model.UserProfile.Gender.MALE;

@Entity
@Table(name = "user_profile")
public class UserProfile {
    public enum Gender {
        MALE,
        FEMALE
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
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

    @Column(name = "attributes_values", columnDefinition = "json")
    @Convert(converter = AttributesValuesConverter.class)
    private Map<ModelAttribute<?>, ModelAttributeValue<?>> attributesValues = new HashMap<>();

    public UserProfile() {
    }

    public UserProfile(User user) {
        this.user = user;
    }

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

    public UserProfile setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", avatar=" + avatar +
                ", gender=" + gender +
                ", attributesValues=" + attributesValues +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(avatar, that.avatar) &&
                gender == that.gender &&
                Objects.equals(attributesValues, that.attributesValues);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, name, surname, birthDate, avatar, gender, attributesValues);
    }
}
