package ru.frtk.das.services;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;
import com.petersamokhin.bots.sdk.utils.vkapi.API;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.frtk.das.TestApplicationConfig;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = TestApplicationConfig.class)
public class ChatBotServiceTests {

    private ChatBotService sut;
    @MockBean
    private Group group;
    private API api;
    private ArgumentCaptor<JSONObject> argumentCaptor;

    @Before
    public void init() {
        api = mock(API.class);
        argumentCaptor = forClass(JSONObject.class);

        when(group.api()).thenReturn(api);
        doNothing().when(api).call(anyString(), argumentCaptor.capture(), any());

        sut = new ChatBotService(group, mock(UserService.class), attributesService);
    }
//    @Ignore
    @Test
    public void onHelloMessage() {
        //when
        sut.onTextMessage(new Message().text("some message that is not addressed to bot").from(group));

        //then
        verify(api, times(0)).call(anyString(), argumentCaptor.capture(), any());

    }

    @Ignore
    @Test
    public void onSetGroupMessage() {
        //when
        sut.onTextMessage(new Message().text("/set гр 517").from(group));

        //then
        JSONObject obj = argumentCaptor.getValue();
        assertEquals("Поле \"гр\" имеет значение \"517\"", obj.getString("message"));
    }
    @Ignore
    @Test
    public void onSetWrongGroupMessage() {
        //when
        sut.onTextMessage(new Message().text("/set гр 17").from(group));

        //then
        JSONObject obj = argumentCaptor.getValue();
        assertEquals("Неправильный формат группы.\n Введите группу в формате Х1Х", obj.getString("message"));
    }

}
