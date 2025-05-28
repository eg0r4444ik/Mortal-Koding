package ru.vsu.rogachev.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommonMessageUtils {

    @NotNull
    public SendMessage createMessageWithButtons(
            @NotNull Long chatId,
            @NotNull String text,
            @NotNull List<String> buttonTexts,
            @NotNull List<String> buttonsCallbackData
    ) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);

        return createMessageWithButtons(sendMessage, buttonTexts, buttonsCallbackData);
    }

    @NotNull
    public SendMessage createMessageWithButtons(
            @NotNull SendMessage sendMessage,
            @NotNull List<String> buttonTexts,
            @NotNull List<String> buttonsCallbackData
    ) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
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

    @NotNull
    public SendMessage createMessageWithTable(
            @NotNull Long chatId,
            @Nullable String header,
            @NotNull List<List<String>> rows
    ) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());

        StringBuilder text = new StringBuilder();

        if (rows.isEmpty()) return sendMessage;

        int columns = rows.get(0).size();
        int[] colWidths = new int[columns];

        for (List<String> row : rows) {
            for (int col = 0; col < columns; col++) {
                if (col < row.size()) {
                    colWidths[col] = Math.max(colWidths[col], row.get(col).length());
                }
            }
        }

        if (header != null && !header.isBlank()) {
            text.append("<b>").append(header).append("</b>\n");
        }

        text.append("<pre>\n");

        for (List<String> row : rows) {
            text.append("|");
            for (int col = 0; col < columns; col++) {
                String cell = col < row.size() ? row.get(col) : "";
                text.append(centerText(cell, colWidths[col])).append("|");
            }
            text.append("\n");
        }

        text.append("</pre>");

        sendMessage.setText(text.toString());
        sendMessage.setParseMode("HTML");

        return sendMessage;
    }

    @NotNull
    private String centerText(@NotNull String text, int width) {
        int padding = width - text.length();
        int left = padding / 2;
        int right = padding - left;
        return " ".repeat(left) + text + " ".repeat(right);
    }

}
