package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vsu.rogachev.connections.CodeforcesConnection;
import ru.vsu.rogachev.connections.MailConnection;
import ru.vsu.rogachev.dto.UserDTO;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.entities.enums.UserState;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.kafka.KafkaProducer;
import ru.vsu.rogachev.services.UserService;
import ru.vsu.rogachev.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private MailConnection mailConnection;
    
    public void registerBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update){
        if(update == null){
            log.error("Received update is null");
            return;
        }

        if(update.getMessage() != null || update.hasCallbackQuery()){
            distributeMessagesByType(update);
        }else{
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void distributeMessagesByType(Update update) {
        var message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();
        String text = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
        long chatId = message.getChatId();
        try {
            User user = getUser(message);
            if(user.getState() == UserState.WAIT_FOR_HANDLE_STATE){
                processWaitingHandleState(user, chatId, text);
            }else if(user.getState() == UserState.WAIT_CONFIRMATION_CODE_STATE){
                processWaitingCodeState(user, chatId, text);
            }else if(user.getState() == UserState.DURING_THE_GAME){
                processDuringTheGameState(user, chatId, text);
            }else if(user.getState() == UserState.BASIC_STATE){
                processBasicState(user, chatId, text);
            }
        }catch (DbDontContainObjectException e){
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(message.getChatId(), "Что-то пошло не так"));
        }
    }

    private void processWaitingHandleState(User user, long chatId, String handle){
        try {
            UserDTO userDTO = codeforcesConnection.getUser(handle);
            userService.setEmail(user, userDTO.getEmail());
            userService.setCodeforcesUsername(user, userDTO.getHandle());
            userService.setState(user, UserState.WAIT_CONFIRMATION_CODE_STATE);
            producer.sendEmail(user.getEmail());
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "На почту был отправлен код подтверждения"));
        }catch (Exception e){
            userService.setEmail(user, null);
            userService.setCodeforcesUsername(user, null);
            userService.setState(user, UserState.WAIT_FOR_HANDLE_STATE);
            telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Похоже такого пользователя не существует" +
                    "на Codeforces или его электронная почта скрыта. Попробуйте еще раз!"));
        }
    }

    private void processWaitingCodeState(User user, long chatId, String code){
        try {
            if (code.equals("Отправить заново")) {
                producer.sendEmail(user.getEmail());
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "На почту был отправлен код подтверждения"));
            } else if (code.equals("Изменить handle")) {
                userService.setState(user, UserState.WAIT_FOR_HANDLE_STATE);
                telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Введите свой handle с сайта Codeforces"));
            } else {
                boolean result = mailConnection.checkCode(user.getEmail(), code);
                if(result){
                    userService.setState(user, UserState.BASIC_STATE);
                    userService.setActive(user, true);
                    telegramBot.sendAnswerMessage(messageUtils.generateSendMessage(chatId, "Вы успешно привязали аккаунт Codeforces с хендлом '"
                            + user.getCodeforcesUsername() + "' к чату! Теперь вы можете соревноваться с другими участниками!"));
                }else{
                    telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithButtons(chatId,
                            "Вы ввели неверный код, попробуйте снова или " +
                            "запросите повторную отправку кода нажав на кнопку", List.of("Отправить код заново", "Ввести другой handle"),
                            List.of("Отправить заново", "Изменить handle")));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void processBasicState(User user, long chatId, String text){
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

    private void processDuringTheGameState(User user, long chatId, String text){

    }

    private User getUser(Message message) throws DbDontContainObjectException {
        long telegramId = message.getChatId();
        String firstName = message.getFrom().getFirstName();
        String lastName = message.getFrom().getLastName();
        String username = message.getFrom().getUserName();
        if(!userService.existsByTelegramId(telegramId)){
            userService.addUser(telegramId, firstName, lastName, username);
        }
        return userService.getUserByTelegramId(telegramId);
    }

}
