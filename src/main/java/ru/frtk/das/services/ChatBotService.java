package ru.frtk.das.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.frtk.das.dataasset.Params;
import ru.frtk.das.model.ModelAttribute;
import ru.frtk.das.model.User;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Comparator;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.diffplug.common.base.Errors.log;
import static com.diffplug.common.base.Errors.rethrow;
import static java.lang.String.format;


@Service
@Profile("with-chat")
public class ChatBotService {

    private static Logger logger = LogManager.getLogger(ChatBotService.class);

    private static final String MESSAGES_FILE = "./src/main/resources/messages.json";

    private static final Pattern HELLO_PATTERN = Pattern.compile("^/hello$");
    private static final Pattern HELP_PATTERN = Pattern.compile("^/help$");
    private static final Pattern PARAMS_PATTERN = Pattern.compile("^/params$");
    private static final Pattern SET_PATTERN = Pattern.compile("^/set \\S+ \\S+$");
    private static final Pattern GET_ALL_PATTERN = Pattern.compile("^/get$");
    private static final Pattern GET_PARAM_PATTERN = Pattern.compile("^/get \\S+$");

    private static final String CREATE_DOCUMENT_STRING = "^/create<br>money:[0-9]+<br>reason:";
    private static final Pattern CREATE_DOCUMENT_PATTERN = Pattern.compile(CREATE_DOCUMENT_STRING+"(\\w|\\W)*");


    private static final Pattern PHONE_PATTERN = Pattern.compile("^.+7.([0-9]{3}.)[0-9]{3}-[0-9]{2}-[0-9]{2}$");
    private static final Pattern GROUP_PATTERN = Pattern.compile("[0-9]1[1-9]");
    private final Group groupChat;
    private final ObjectMapper mapper;
    private final VkApiClient vk;
    private final GroupActor actor;
    private final UserService userService;
    private final AttributesService attributesService;
    private JsonNode localizedMessages;

    @Autowired
    public ChatBotService(Group groupChat, UserService userService, AttributesService attributesService) {
        this.groupChat = groupChat;
        this.userService = userService;
        this.attributesService = attributesService;
        this.mapper = new ObjectMapper();
        this.vk = new VkApiClient(HttpTransportClient.getInstance());
        this.actor = new GroupActor(groupChat.getId(), groupChat.getAccessToken());
        this.localizedMessages = rethrow().get(() -> mapper.readTree(new File(MESSAGES_FILE)));
    }

    @PostConstruct
    public void registerMessageHandlers() {
        groupChat.onSimpleTextMessage(t -> log().run(() -> this.onTextMessage(t)));
    }

    protected void onTextMessage(Message message) {
        String text = message.getText();
        if (text.startsWith("/")) { // Command for bot
            try(final CloseableThreadContext.Instance ctc =
                    loggingSetup(userService.userByVkIdOrRegister(message.authorId().longValue()))) {
                logger.debug("Incoming request: {}", text);
                executeCommand(message);
            }
        }
    }

    /**
     * This method process command in agreement with patterns
     * @param message
     */
    protected void executeCommand(Message message) {
        String text = message.getText();
        if (HELLO_PATTERN.matcher(text).find()){
            returnMessageFromFile(message.authorId(), "hello message");
        } else if(PARAMS_PATTERN.matcher(text).find()){
            returnAvailableParams(message.authorId());
        } else if(HELP_PATTERN.matcher(text).find()){
            returnMessageFromFile(message.authorId(), "help message");
        } else if (SET_PATTERN.matcher(text).find()) {
            String[] array = text.split(" ");
            if (array.length < 3) {
                returnMessageFromFile(message.authorId(), "wrong number of parameters");
            }
            setParam(message.authorId(), array[1], array[2]);
        } else if (GET_ALL_PATTERN.matcher(text).find()) {
            getAllParams(message.authorId());
        } else if (GET_PARAM_PATTERN.matcher(text).find()) {
            String[] array = text.split(" ");
            if (array.length < 2) {
                returnMessageFromFile(message.authorId(), "wrong number of parameters");
            }
            getParam(message.authorId(), array[1]);
        }else if(CREATE_DOCUMENT_PATTERN.matcher(text).find()){
            createDocument(message);
        } else {
            returnMessageFromFile(message.authorId(), "unknown command");
        }
    }

    private void returnAvailableParams(Integer userVkId) {
        String responseText = attributesService.allAttributesForProfile().stream()
                .sorted(Comparator.comparing(ModelAttribute::getAttributeDescription))
                .map(a -> String.format("%s - %s", a.getAttributeName(), a.getAttributeDescription()))
                .collect(StringBuilder::new,
                        (o, s) -> o.append('\n').append(s),
                        (o1, o2) -> o1.append('\n').append(o2.toString())
                )
                .toString();
        returnMessage(userVkId, responseText);
    }

    private void createDocument(Message message){
        String text = message.getText();
        //TODO: check that all params about user are presented
        String money = text.split("<br>")[1];
        //TODO: use this params to create a document
        Double amount = Double.valueOf(money.split(":")[1]);
        String reason = text.split(CREATE_DOCUMENT_STRING)[1];

        String s = format("amount:\n%f\nreason:\n%s", amount, reason);
        returnMessage(message.authorId(), s);
    }

    /**
     * Checks the validity of param and sets it in database
     * @param userId
     * @param param - value {@link Params#commandValue}
     */
    private void setParam(Integer userId, String param, String value) {
        User user = userService.userByVkIdOrRegister(userId.longValue());
        if(!userService.changeAttributeValue(user, param, value)) {
            returnMessage(userId, format("No attribute %s", param));
        }

        getParam(userId, param);
    }

    /**
     * Sends the information about User that contains in database
     * @param userId
     */
    private void getAllParams(Integer userId){
        //TODO: get Info from DB and send response
        new Message()
                .from(groupChat)
                .to(userId)
                .text(format(""))
                .send();
    }

    /**
     * Checks the validity of param and sends info about this field
     * @param userId
     * @param param - value {@link Params#commandValue}
     */
    private void getParam(Integer userId, String param) {
        if (!attributesService.isSupportedAttribute(param)) {
            returnMessage(userId, format("Not supported attribute %s", param));
            return;
        }

        String responseText = attributesService.findAttribute(param)
                .map(a -> userService.userByVkIdOrRegister(userId.longValue())
                        .getProfile()
                        .getAttributesValues()
                        .get(a)
                ).map(v -> format("Value: %s", v.value.templateValue()))
                .orElse("No value");

        new Message()
                .from(groupChat)
                .to(userId)
                .text(responseText)
                .send();
    }


    /**
     * Sends the message from {@link #MESSAGES_FILE}
     * @param userId
     * @param element - key in file
     */
    private void returnMessageFromFile(Integer userId, String element){
        returnMessage(userId, localizedMessages.get(element).asText());
    }

    /**
     * Sends the 'text' to user
     * @param userId
     * @param text
     */
    private void returnMessage (Integer userId, String text){
        new Message()
                .from(groupChat)
                .to(userId)
                .text(text)
                .send();
    }

    private CloseableThreadContext.Instance loggingSetup(User user) {
        return CloseableThreadContext
                .put("userId", user.getId().toString())
                .put("requestId", format("%x", UUID.randomUUID().getLeastSignificantBits()));
    }
}
