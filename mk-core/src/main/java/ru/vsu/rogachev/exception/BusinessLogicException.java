package ru.vsu.rogachev.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessLogicException extends RuntimeException {

    @Nullable
    @JsonProperty("chat_id")
    private Long chatId;

    @NotNull
    @JsonProperty("text")
    private String text;

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
