package ru.vsu.rogachev.service.message;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
public interface MessageSender {

    void sendMessage(@NotNull Long chatId, @NotNull String text);

    default void sendMessage(@NotNull List<Long> chatIds, @NotNull String text) {
        for (Long chatId : chatIds) {
            sendMessage(chatId, text);
        }
    }

    void sendMessage(@NotNull SendMessage sendMessage);

    default void sendMessage(@NotNull List<SendMessage> sendMessages) {
        for (SendMessage sendMessage : sendMessages) {
            sendMessage(sendMessage);
        }
    }

}
