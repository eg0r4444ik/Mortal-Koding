package ru.vsu.rogachev.service.message;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vsu.rogachev.controller.TelegramBot;

@Primary
@Service
@RequiredArgsConstructor
public class DefaultMessageSender implements MessageSender {

    private final TelegramBot telegramBot;

    @Override
    public void sendMessage(@NotNull Long chatId, @NotNull String text) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);

        telegramBot.sendAnswerMessage(sendMessage);
    }

    @Override
    public void sendMessage(@NotNull SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

}
