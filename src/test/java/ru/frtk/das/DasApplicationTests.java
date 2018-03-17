package ru.frtk.das;

import com.petersamokhin.bots.sdk.clients.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Import(value = TestApplicationConfig.class)
public class DasApplicationTests {

    @MockBean
    private Group group;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/health-check",
                String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
