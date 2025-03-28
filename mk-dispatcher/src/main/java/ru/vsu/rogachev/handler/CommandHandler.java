package ru.vsu.rogachev.handler;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vsu.rogachev.entity.User;

public interface CommandHandler {

    void execute(@NotNull User user, @NotNull Long chatId, @NotNull String message);

}
