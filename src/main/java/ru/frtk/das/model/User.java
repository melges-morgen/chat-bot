package ru.frtk.das.model;

import org.hibernate.annotations.GenericGenerator;
import ru.frtk.das.microtypes.Email;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "vk_id", unique = true)
    private Long vkId;

    @Column(name = "email")
    private Email email;

    @Column(name = "salt")
    private String salt;

    @Column(name = "password")
    private String hashedPassword;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.PERSIST)
    private UserProfile profile = new UserProfile(this);

    public static User user() {
        return new User();
    }

    public UUID getId() {
        return id;
    }

    public User setId(UUID id) {
        this.id = id;
        return this;
    }

    public Long getVkId() {
        return vkId;
    }

    public User setVkId(Long vkId) {
        this.vkId = vkId;
        return this;
    }

    public Email getEmail() {
        return email;
    }

    public User setEmail(Email email) {
        this.email = email;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public User setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public User setProfile(UserProfile profile) {
        this.profile = profile;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", vkId=" + vkId +
                ", email=" + email +
                ", salt=<secret>" +
                ", hashedPassword=<secret>" +
                ", profile=" + profile +
                '}';
    }
}
