package ru.frtk.das.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.frtk.das.microtypes.TemplateValue;
import ru.frtk.das.model.*;

import javax.transaction.Transactional;

import static com.diffplug.common.base.Errors.rethrow;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final UserProfileRepository userProfileRepo;
    private final VkService vkService;
    private final ModelAttributeRepository attributeRepo;

    @Autowired
    public UserService(final UserRepository userRepo,
                       final UserProfileRepository userProfileRepo,
                       final VkService vkService,
                       final ModelAttributeRepository attributeRepo) {
        this.userRepo = userRepo;
        this.userProfileRepo = userProfileRepo;
        this.vkService = vkService;
        this.attributeRepo = attributeRepo;
    }

    @Transactional
    public User userByVkIdOrRegister(Long vkId) {
        return userRepo.findUserByVkId(vkId)
                .orElseGet(() -> userRepo.save(vkService.fetchUser(vkId)));
    }

    @Transactional
    public boolean changeAttributeValue(User user, String attributeName, String value) {
        return attributeRepo.findByAttributeName(attributeName)
                .map(a -> ModelAttributeValue.<TemplateValue>modelAttributeValue(
                        a,
                        rethrow().get(() -> a.getAttributeClass().getConstructor(String.class).newInstance(value)))
                )
                .map(v -> userProfileRepo.getUserProfileByUser(user).setAttributeValue(v.attribute, v))
                .map(userProfileRepo::save)
                .isPresent();

    }
}
