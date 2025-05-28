package ru.vsu.rogachev.client.mk.container;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.exception.BusinessLogicExceptions;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseContainer<T> {

    @Nullable
    @JsonProperty("exception")
    private BusinessLogicException exception;

    @Nullable
    @JsonProperty("response")
    private T response;

    @NotNull
    @JsonProperty("status")
    private ResponseStatus status;

    @NotNull
    public static <T> ResponseContainer<T> success(@Nullable T response) {
        return new ResponseContainer<>( null, response, ResponseStatus.SUCCESS);
    }

    @NotNull
    public static <T> ResponseContainer<T> withException(@NotNull BusinessLogicException exception) {
        return new ResponseContainer<>(exception, null, ResponseStatus.ERROR);
    }

    @NotNull
    public static <T> ResponseContainer<T> withException(@NotNull BusinessLogicExceptions exceptionType) {
        return new ResponseContainer<>(BusinessLogicException.of(exceptionType), null, ResponseStatus.ERROR);
    }

    public enum ResponseStatus {
        SUCCESS,
        ERROR
    }

}
