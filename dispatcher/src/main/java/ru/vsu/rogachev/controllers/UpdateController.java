package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vsu.rogachev.connections.CodeforcesConnection;
import ru.vsu.rogachev.dto.UserDTO;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.entities.enums.UserState;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.kafka.KafkaProducer;
import ru.vsu.rogachev.services.UserService;
import ru.vsu.rogachev.utils.MessageUtils;

@Component
@Log4j
public class UpdateController {

    private TelegramBot telegramBot;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private UserService userService;

    @Autowired
    private CodeforcesConnection codeforcesConnection;
    
    public void registerBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update){
        if(update == null){
            log.error("Received update is null");
            return;
        }

        if(update.getCallbackQuery() != null){
            log.debug(update.getCallbackQuery().getData());
            return;
        }

        if(update.getMessage() != null){
            distributeMessagesByType(update);
        }else{
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();
        long chatId = message.getChatId();
        try {
            User user = getUser(message);
            if(user.getState() == UserState.WAIT_FOR_HANDLE_STATE){
                String handle = message.getText();
                UserDTO userDTO = codeforcesConnection.getUser(handle);
                userService.setEmail(user, userDTO.getEmail());
                userService.setCodeforcesUsername(user, userDTO.getHandle());
                userService.setState(user, UserState.WAIT_CONFIRMATION_CODE_STATE);
                producer.sendEmail(user.getEmail());
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "На почту был отправлен код подтверждения"));
            }else if(user.getState() == UserState.WAIT_CONFIRMATION_CODE_STATE){

            }else if(user.getState() == UserState.DURING_THE_GAME){

            }else if(user.getState() == UserState.BASIC_STATE){
                if(user.getIsActive()){

                }else{
                    telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId,
                            "Похоже вы еще не привязали свой аккаунт на сайте Codeforces с этим чатом. Введите свой handle " +
                                    "с сайта https://codeforces.com/ и введите код подтверждения, который придет вам на почту," +
                                    "привязанную к вашему аккаунту codeforces. !! Если вы скрыли свою почту на codeforces, " +
                                    "сделайте ее публичной !!"));
                    userService.setState(user, UserState.WAIT_FOR_HANDLE_STATE);
                }
            }
        }catch (DbDontContainObjectException e){
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Что-то пошло не так"));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }



    private User getUser(Message message) throws DbDontContainObjectException {
        long telegramId = message.getFrom().getId();
        String firstName = message.getFrom().getFirstName();
        String lastName = message.getFrom().getLastName();
        String username = message.getFrom().getUserName();
        if(!userService.existsByTelegramId(telegramId)){
            userService.addUser(telegramId, firstName, lastName, username);
        }
        return userService.getUserByTelegramId(telegramId);
    }

}
