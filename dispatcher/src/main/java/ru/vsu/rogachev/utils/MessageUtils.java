package ru.vsu.rogachev.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageUtils {

    public SendMessage generateSendMessageWithText(Update update, String text){
        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Тык");
        inlineKeyboardButton.setCallbackData("Привет");

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(inlineKeyboardButton);
        keyboardButtons.add(inlineKeyboardButton);
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(keyboardButtons);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

}
