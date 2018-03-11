package ru.frtk.das.configuration;

import com.petersamokhin.bots.sdk.clients.Group;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@EnableConfigurationProperties(VkConfig.class)
public class ApplicationConfig {

    @Bean
    @Lazy
    public Group vkBotGroup(VkConfig config) {
        return new Group(config.getGroupId(), config.getAccessToken());
    }
}
