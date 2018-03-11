package ru.frtk.das;

import com.petersamokhin.bots.sdk.clients.Group;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import ru.frtk.das.configuration.ApplicationConfig;

import static org.mockito.Mockito.mock;

@Configuration
public class TestApplicationConfig {
    @Bean
    @Primary
    public Group testVkBotGroup() {
        return mock(Group.class);
    }
}
