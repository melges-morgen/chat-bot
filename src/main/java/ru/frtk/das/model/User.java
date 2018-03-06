package ru.frtk.das.model;

import ru.frtk.das.microtypes.Email;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "vk_id", unique = true)
    private Long vkId;

    @Column(name = "email")
    private Email email;

    @Column(name = "salt")
    private String salt;

    @Column(name = "password")
    private String hashedPassword;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private UserProfile profile;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getVkId() {
        return vkId;
    }

    public void setVkId(Long vkId) {
        this.vkId = vkId;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
