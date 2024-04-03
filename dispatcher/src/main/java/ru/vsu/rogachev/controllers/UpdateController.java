package ru.vsu.rogachev.controllers;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vsu.rogachev.utils.MessageUtils;

@Component
@Log4j
public class UpdateController {

    private TelegramBot telegramBot;
    @Autowired
    private MessageUtils messageUtils;
    
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
        String s = message.getFrom().getFirstName() + " " + message.getFrom().getLastName()
                + " " + message.getFrom().getUserName();
        telegramBot.sendAnswerMessage(messageUtils.generateSendMessageWithText(update, s));
    }

}
