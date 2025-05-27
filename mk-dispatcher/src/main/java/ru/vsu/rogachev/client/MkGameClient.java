package ru.vsu.rogachev.client;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vsu.rogachev.client.codeforces.dto.CodeforcesResponseContainer;
import ru.vsu.rogachev.client.codeforces.dto.CodeforcesUser;
import ru.vsu.rogachev.client.codeforces.dto.Problems;
import ru.vsu.rogachev.client.codeforces.dto.Submission;
import ru.vsu.rogachev.client.mk.container.ResponseContainer;
import ru.vsu.rogachev.client.mk.game.dto.rest.GetGameStateResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.vsu.rogachev.client.mk.auth.AuthEndpoints.SEND_CODE_ENDPOINT;
import static ru.vsu.rogachev.client.mk.container.ResponseContainer.ResponseStatus.ERROR;
import static ru.vsu.rogachev.client.mk.game.GameEndpoints.GET_GAME_STATE_ENDPOINT;
import static ru.vsu.rogachev.config.client.MkGameClientConfig.MK_GAME_CLIENT_NAME;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.COMMON_LOGIC_EXCEPTION;

@Service
public class MkGameClient {

    private final WebClient webClient;

    public MkGameClient(@Qualifier(MK_GAME_CLIENT_NAME) WebClient webClient) {
        this.webClient = webClient;
    }

    public GetGameStateResponse getGameState(@NotNull String handle){
        ResponseContainer<GetGameStateResponse> response = webClient.post()
                .uri(GET_GAME_STATE_ENDPOINT)
                .bodyValue(handle)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseContainer<GetGameStateResponse>>() {})
                .blockOptional()
                .orElse(ResponseContainer.withException(COMMON_LOGIC_EXCEPTION));

        if (response.getStatus() == ERROR) {
            throw Objects.requireNonNull(response.getException());
        }

        return response.getResponse();
    }

}
