package ru.frtk.das.services;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.frtk.das.model.User;
import ru.frtk.das.model.UserProfile.Gender;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.diffplug.common.base.Errors.log;
import static com.diffplug.common.base.Errors.rethrow;
import static com.vk.api.sdk.objects.base.Sex.FEMALE;
import static com.vk.api.sdk.queries.users.UserField.*;
import static ru.frtk.das.model.User.user;

@Service
public class VkService {
    private final VkApiClient client;
    private final GroupActor actor;

    @Autowired
    public VkService(VkApiClient client, GroupActor actor) {
        this.client = client;
        this.actor = actor;
    }


    public User fetchUser(Long vkId) {
        return rethrow().get(() -> client.users().get(actor)
                .userIds(vkId.toString())
                .fields(SEX, BDATE, PHOTO_MAX_ORIG)
                .execute())
                .stream()
                .map(ur -> {
                            User user = user().setVkId(ur.getId().longValue());
                            user.getProfile()
                                    .setName(ur.getFirstName())
                                    .setSurname(ur.getLastName())
                                    .setBirthDate(log().wrapWithDefault(
                                            () -> LocalDate.parse(
                                                    ur.getBdate(),
                                                    DateTimeFormatter.ofPattern("d.M.uuuu")
                                            ),
                                           null
                                    ).get())
                                    .setGender(ur.getSex() == FEMALE ? Gender.FEMALE : Gender.MALE)
                                    .setAvatar(URI.create(ur.getPhotoMaxOrig()));
                            return user;
                        }
                )
                .findAny()
                .orElse(user().setVkId(vkId));
    }
}
