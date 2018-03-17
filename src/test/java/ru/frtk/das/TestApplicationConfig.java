package ru.frtk.das;

import com.petersamokhin.bots.sdk.clients.Group;
import ru.frtk.das.configuration.ApplicationConfig;
import ru.frtk.das.configuration.VkConfig;

import static org.mockito.Mockito.mock;

public class TestApplicationConfig extends ApplicationConfig {
    @Override
    public Group vkBotGroup(VkConfig config) {
        return mock(Group.class);
    }
}
