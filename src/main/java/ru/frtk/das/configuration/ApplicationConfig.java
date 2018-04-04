package ru.frtk.das.configuration;

import com.petersamokhin.bots.sdk.clients.Group;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
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

    @Bean
    public VkApiClient vkApiClient() {
        return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public GroupActor vkActor(VkConfig config) {
        return new GroupActor(config.getGroupId(), config.getAccessToken());
    }
}
