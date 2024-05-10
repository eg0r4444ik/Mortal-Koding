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

    public SendMessage generateSendMessageWithTable(Long chatId, List<Long> player1Points, List<Long> player2Points,
                                                    String player1Handle, String player2Handle){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());

        StringBuilder text = new StringBuilder();
        text.append("<b>Состояние соревнования:</b>\n");
        text.append("<pre>\n");

        player1Handle = " " + player1Handle + " ";
        player2Handle = " " + player2Handle + " ";
        int len1 = player1Handle.length();
        int len2 = player2Handle.length();

        text.append("          |").append(player1Handle).append("|").append(player2Handle).append("\n");

        Long player1Sum = 0L;
        Long player2Sum = 0L;
        for(int i = 0; i < player1Points.size(); i++){
            Long pts1 = player1Points.get(i);
            Long pts2 = player2Points.get(i);
            player1Sum += pts1;
            player2Sum += pts2;
            int pts1Len = pts1.toString().length();
            int pts2Len = pts2.toString().length();
            text.append("Задача ").append(i+1).append(": ").append("|");
            for(int j = 0; j < (len1-pts1Len)/2; j++){
                text.append(" ");
            }
            text.append(pts1);
            for(int j = (len1-pts1Len)/2 + pts1Len; j < len1; j++){
                text.append(" ");
            }
            text.append("|");

            for(int j = 0; j < (len2-pts2Len)/2; j++){
                text.append(" ");
            }
            text.append(pts2);
            for(int j = (len2-pts2Len)/2 + pts2Len; j < len2; j++){
                text.append(" ");
            }
            text.append("\n");
        }

        text.append("Сумма:    |");
        int pts1Len = player1Sum.toString().length();
        int pts2Len = player2Sum.toString().length();
        for(int j = 0; j < (len1-pts1Len)/2; j++){
            text.append(" ");
        }
        text.append(player1Sum);
        for(int j = (len1-pts1Len)/2 + pts1Len; j < len1; j++){
            text.append(" ");
        }
        text.append("|");

        for(int j = 0; j < (len2-pts2Len)/2; j++){
            text.append(" ");
        }
        text.append(player2Sum);
        for(int j = (len2-pts2Len)/2 + pts2Len; j < len2; j++){
            text.append(" ");
        }

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
