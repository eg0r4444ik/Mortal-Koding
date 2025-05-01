package ru.vsu.rogachev.service.handler;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;

public interface CommandHandler {

    void execute(@NotNull User user, @NotNull Long chatId, @NotNull String message);

    UserState getHandledState();

}
