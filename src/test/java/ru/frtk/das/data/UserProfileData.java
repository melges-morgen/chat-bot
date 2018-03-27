package ru.frtk.das.data;

import ru.frtk.das.model.UserProfile;

import java.time.LocalDate;
import java.util.UUID;

public interface UserProfileData {
    static UserProfile aUserProfile() {
        return new UserProfile()
                .setId(UUID.nameUUIDFromBytes("aUserProfile".getBytes()))
                .setName("Test")
                .setSurname("Testovich")
                .setBirthDate(LocalDate.of(1998, 1, 12));
    }
}
