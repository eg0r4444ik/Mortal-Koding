package ru.vsu.rogachev.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vsu.rogachev.config.TelegramBotProperties;
import ru.vsu.rogachev.exception.BusinessLogicException;

@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramBotProperties properties;

    private final UpdateController updateController;

    public TelegramBot(TelegramBotProperties properties, @Lazy UpdateController updateController) {
        this.properties = properties;
        this.updateController = updateController;
    }

    @Override
    public String getBotUsername() {
        return properties.getName();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            updateController.processUpdate(update);
        } catch (BusinessLogicException ex) {
            if (ex.getChatId() == null) {
                log.error(ex.getMessage(), ex);
                return;
            }
            var sendMessage = new SendMessage();
            sendMessage.setChatId(ex.getChatId().toString());
            sendMessage.setText(ex.getText());

            sendAnswerMessage(sendMessage);
        }
    }

    public void sendAnswerMessage(SendMessage message){
        if(message != null){
            try{
                execute(message);
            }catch (TelegramApiException e){
                log.error(e);
            }
        }
    }

}
