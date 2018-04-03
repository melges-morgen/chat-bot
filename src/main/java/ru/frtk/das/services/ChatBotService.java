package ru.frtk.das.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import com.vk.api.sdk.queries.users.UsersNameCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.frtk.das.dataasset.Params;

import javax.annotation.PostConstruct;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


@Service
@Profile("with-chat")
public class ChatBotService {

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
    private JsonNode node;

    @Autowired
    public ChatBotService(Group groupChat) {
        this.groupChat = groupChat;
        this.mapper = new ObjectMapper();
        this.vk = new VkApiClient(HttpTransportClient.getInstance());
        this.actor = new GroupActor(groupChat.getId(), groupChat.getAccessToken());

    }

    @PostConstruct
    public void onInit(){
        try {
            node = mapper.readTree(new File(MESSAGES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostConstruct
    public void registerMessageHandlers() {
        groupChat.onSimpleTextMessage(this::onTextMessage);
    }

    protected void onTextMessage(Message message) {
        String text = message.getText();
        if (text.startsWith("/")) {
            //the message is addressed to the bot
            //Check User in DB
            //TODO: if there is no User in DB, create it
            if (false) {
                try {
                    List<UserXtrCounters> users = vk.users()
                            .get(actor)
                            .userIds(String.valueOf(message.authorId()))
                            .fields(UserField.CONTACTS)
                            .nameCase(UsersNameCase.GENITIVE)
                            .execute();
                    UserXtrCounters user = users.get(0);

                } catch (ApiException e) {
                    e.printStackTrace();
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            executeCommand(message);
        } else {
            return;
        }

//        new Message()
//                .from(groupChat)
//                .to(message.authorId())
//                .text(String.format("Your message was: %s", message.getText()))
//                .send();
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
            returnMessageFromFile(message.authorId(), "params");
        } else if(HELP_PATTERN.matcher(text).find()){
            returnMessageFromFile(message.authorId(), "help message");
        } else if (SET_PATTERN.matcher(text).find()) {
            String[] array = text.split(" ");
            setParam(message.authorId(), array[1], array[2]);
        } else if (GET_ALL_PATTERN.matcher(text).find()) {
            getAllParams(message.authorId());
        } else if (GET_PARAM_PATTERN.matcher(text).find()) {
            String[] array = text.split(" ");
            getParam(message.getMessageId(), array[1]);
        }else if(CREATE_DOCUMENT_PATTERN.matcher(text).find()){
            createDocument(message);
        } else {
            returnMessageFromFile(message.authorId(), "unknown command");
        }
    }

    private void createDocument(Message message){
        String text = message.getText();
        //TODO: check that all params about user are presented
        String money = text.split("<br>")[1];
        //TODO: use this params to create a document
        Double amount = Double.valueOf(money.split(":")[1]);
        String reason = text.split(CREATE_DOCUMENT_STRING)[1];

        String s = String.format("amount:\n%f\nreason:\n%s", amount, reason);
        returnMessage(message.authorId(), s);
    }

    /**
     * Checks the validity of param and sets it in database
     * @param userId
     * @param param - value {@link Params#commandValue}
     */
    private void setParam(Integer userId, String param, String value) {
        Params settingParam = Params.getParam(param);
        if(null == settingParam){
            returnMessage(userId, "Неправильная команда. Список команд можно узнать /help");
            return;
        }
        //checks validity of phone number
        if(Params.PHONE_NUMBER == settingParam){
            if (!PHONE_PATTERN.matcher(value).find()){
                returnMessageFromFile(userId, "wrong telephone number");
                return;
            }
        }
        //checks validity of group number
        if(Params.GROUP_NUMBER == settingParam){
            if (!GROUP_PATTERN.matcher(value).find()){
                returnMessageFromFile(userId, "wrong group number");
                return;
            }
        }

        //TODO: set param to user

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
                .text(String.format(""))
                .send();
    }

    /**
     * Checks the validity of param and sends info about this field
     * @param userId
     * @param param - value {@link Params#commandValue}
     */
    private void getParam(Integer userId, String param){


        Params gettingParam = Params.getParam(param);
        if(null == gettingParam){
            returnMessage(userId, "Неправильная команда. Список команд можно узнать /help");
            return;
        }

        //TODO: get Info from DB and send response
            String value = null;


        new Message()
                .from(groupChat)
                .to(userId)
                .text(String.format("Поле \"%s\" имеет значение \"%s\"", param, value))
                .send();
    }


    /**
     * Sends the message from {@link #MESSAGES_FILE}
     * @param userId
     * @param element - key in file
     */
    private void returnMessageFromFile(Integer userId, String element){
        returnMessage(userId, node.get(element).asText());
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
}
