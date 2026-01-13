package ru.vsu.rogachev.service.handler;

import org.jetbrains.annotations.NotNull;
import ru.vsu.rogachev.entity.dao.User;
import ru.vsu.rogachev.entity.enums.UserState;

public interface CommandHandler {

    void execute(@NotNull User user, @NotNull Long chatId, @NotNull String message);

    UserState getHandledState();

}
