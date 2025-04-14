package ru.vsu.rogachev.exception;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class BusinessLogicException extends RuntimeException {

    private final Long chatId;

    private final String text;

    public BusinessLogicException(@NotNull Long chatId, @NotNull String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public static BusinessLogicException of(
            @NotNull Long chatId,
            @NotNull BusinessLogicExceptionType type,
            @NotNull Object... args
    ) {
        return new BusinessLogicException(chatId, String.format(type.getText(), args));
    }
}
