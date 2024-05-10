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

    public SendMessage generateSendMessage(Long chatId, String text){
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);

        return sendMessage;
    }

    public SendMessage generateSendMessageWithButtons(Long chatId, String text,
                                                      List<String> buttonTexts, List<String> buttonsCallbackData){
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);

        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();

        for(int i = 0; i < buttonTexts.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(buttonTexts.get(i));
            inlineKeyboardButton.setCallbackData(buttonsCallbackData.get(i));
            keyboardButtons.add(inlineKeyboardButton);
        }

        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(keyboardButtons);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage generateSendMessageWithTable(Long chatId, List<Long> player1Points, List<Long> player2Points){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());

        StringBuilder player1 = new StringBuilder();
        long player1Sum = 0;
        for(int i = 0; i < player1Points.size(); i++){
            long curr = player1Points.get(i);
            player1Sum += curr;
            player1.append("| ");
            if(curr == 0){
                player1.append(" ");
            }
            player1.append(curr);
            player1.append(" баллов ");
            if(curr == 0){
                player1.append(" ");
            }
            if(i == player1Points.size()-1){
                player1.append("|");
            }
        }
        player1.append("  ");
        player1.append(player1Sum);
        player1.append(" \n");

        StringBuilder player2 = new StringBuilder();
        long player2Sum = 0;
        for(int i = 0; i < player2Points.size(); i++){
            long curr = player2Points.get(i);
            player2Sum += curr;
            player2.append("| ");
            if(curr == 0){
                player2.append(" ");
            }
            player2.append(curr);
            player2.append(" баллов ");
            if(curr == 0){
                player2.append(" ");
            }
            if(i == player2Points.size()-1){
                player2.append("|");
            }
        }
        player2.append("  ");
        player2.append(player2Sum);
        player2.append(" \n");

        String separator = getSeparator(Math.max(player1.length(), player2.length()));

        StringBuilder text = new StringBuilder();
        text.append("<b>Состояние соревнования:</b>\n");
        text.append("<pre>\n");

        for(int i = 1; i <= player1Points.size(); i++){
            text.append("|  Задача ");
            text.append(i);
            text.append("  ");
            if(i == player1Points.size()){
                text.append("|");
            }
        }
        text.append(" Сумма \n");

        text.append(separator);
        text.append(player1);
        text.append(separator);
        text.append(player2);
        text.append(separator);
        text.append("</pre>");

        sendMessage.setText(text.toString());
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    private String getSeparator(int len){
        StringBuilder separator = new StringBuilder();
        for(int i = 0; i < len; i++){
            separator.append("-");
        }
        separator.append("\n");
        return separator.toString();
    }

    public String startGameMessage(){
        return "Соревнование началось";
    }

    public String getGameTasks(List<String> problems){
        StringBuilder messageToUser = new StringBuilder();
        messageToUser.append("Задачи соревнования: \n");
        int curr = 1;

        for (String url : problems) {
            messageToUser.append("Задача ").append(curr).append(": ").append(url).append("\n");
            curr++;
        }

        return messageToUser.toString();
    }

    public String endGameMessage(){
        return "Игра окончена! Результаты представлены в таблице";
    }

}
