package ru.vsu.rogachev.exception;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class BusinessLogicException extends RuntimeException {

    @Nullable
    private final Long chatId;

    @NotNull
    private final String text;

    public BusinessLogicException(@NotNull Long chatId, @NotNull String text) {
        super(text);
        this.chatId = chatId;
        this.text = text;
    }

    public BusinessLogicException(@NotNull String text) {
        super(text);
        this.chatId = null;
        this.text = text;
    }

    public static BusinessLogicException of(
            @NotNull BusinessLogicExceptions type,
            @NotNull Object... args
    ) {
        return new BusinessLogicException(String.format(type.getText(), args));
    }

    public static BusinessLogicException of(
            @NotNull Long chatId,
            @NotNull BusinessLogicExceptions type,
            @NotNull Object... args
    ) {
        return new BusinessLogicException(chatId, String.format(type.getText(), args));
    }
}
