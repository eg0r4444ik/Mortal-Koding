package ru.vsu.rogachev.client;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vsu.rogachev.client.mk.auth.dto.ApiCheckCodeRequest;
import ru.vsu.rogachev.client.mk.dto.ResponseContainer;

import java.util.Objects;

import static ru.vsu.rogachev.client.mk.auth.AuthEndpoints.CHECK_CODE_ENDPOINT;
import static ru.vsu.rogachev.client.mk.auth.AuthEndpoints.SEND_CODE_ENDPOINT;
import static ru.vsu.rogachev.client.mk.dto.ResponseContainer.ResponseStatus.ERROR;
import static ru.vsu.rogachev.config.client.MkAuthClientConfig.MK_AUTH_CLIENT_NAME;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.COMMON_LOGIC_EXCEPTION;

@Service
public class MkAuthClient {

    private final WebClient webClient;

    public MkAuthClient(@Qualifier(MK_AUTH_CLIENT_NAME) WebClient webClient) {
        this.webClient = webClient;
    }

    public void sendCode(@NotNull String email){
        ResponseContainer<Void> response = webClient.post()
                .uri(SEND_CODE_ENDPOINT)
                .bodyValue(email)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseContainer<Void>>() {})
                .blockOptional()
                .orElse(ResponseContainer.withException(COMMON_LOGIC_EXCEPTION));

        if (response.getStatus() == ERROR) {
            throw Objects.requireNonNull(response.getException());
        }
    }

    public boolean checkCode(@NotNull String email, @NotNull String code){
        ApiCheckCodeRequest request = new ApiCheckCodeRequest(email, code);
        ResponseContainer<Boolean> response = webClient.post()
                .uri(CHECK_CODE_ENDPOINT)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseContainer<Boolean>>() {})
                .blockOptional()
                .orElse(ResponseContainer.withException(COMMON_LOGIC_EXCEPTION));

        if (response.getStatus() == ERROR) {
            throw Objects.requireNonNull(response.getException());
        }

        return Objects.requireNonNull(response.getResponse());
    }

}
