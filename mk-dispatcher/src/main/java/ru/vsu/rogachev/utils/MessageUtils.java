package ru.vsu.rogachev.utils;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.vsu.rogachev.controller.TelegramBot;

import java.util.ArrayList;
import java.util.List;

import static ru.vsu.rogachev.handler.BasicStateCommandHandler.ProcessedCommand.FIND_GAME_COMMAND;
import static ru.vsu.rogachev.handler.BasicStateCommandHandler.ProcessedCommand.LOOK_RATING_COMMAND;
import static ru.vsu.rogachev.handler.BasicStateCommandHandler.ProcessedCommand.PLAY_WITH_FRIEND_COMMAND;

@Component
@RequiredArgsConstructor
public class MessageUtils {

    public static final List<String> BASIC_STATE_BUTTON_TEXTS =
            List.of("Найти дуэль", "Играть с другом", "Посмотреть рейтинг");

    public static final List<String> BASIC_STATE_BUTTON_CALLBACK_DATA =
            List.of(FIND_GAME_COMMAND.getMessage(), PLAY_WITH_FRIEND_COMMAND.getMessage(), LOOK_RATING_COMMAND.getMessage());

    private final TelegramBot telegramBot;

    public void sendMessage(@NotNull Long chatId, @NotNull String text){
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);

        telegramBot.sendAnswerMessage(sendMessage);
    }

    public void sendBasicStateMessage(@NotNull Long chatId, @NotNull String text){
        sendMessageWithButtons(chatId, text, BASIC_STATE_BUTTON_TEXTS, BASIC_STATE_BUTTON_CALLBACK_DATA);
    }

    public void sendMessageWithButtons(
            @NotNull Long chatId,
            @NotNull String text,
            @NotNull List<String> buttonTexts,
            @NotNull List<String> buttonsCallbackData
    ){
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

        telegramBot.sendAnswerMessage(sendMessage);
    }

    public void generateSendMessageWithTable(
            @NotNull Long chatId,
            @NotNull List<Long> player1Points,
            @NotNull List<Long> player2Points,
            @NotNull String player1Handle,
            @NotNull String player2Handle
    ){
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

        telegramBot.sendAnswerMessage(sendMessage);
    }

    private String getSeparator(int len){
        StringBuilder separator = new StringBuilder();
        for(int i = 0; i < len; i++){
            separator.append("-");
        }
        separator.append("\n");
        return separator.toString();
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
