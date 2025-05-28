package ru.vsu.rogachev.service.handler;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;

import java.io.IOException;

public interface CommandHandler {

    void execute(@NotNull User user, @NotNull Long chatId, @NotNull String message);

    UserState getHandledState();

}
