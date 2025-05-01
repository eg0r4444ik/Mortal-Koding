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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.vsu.rogachev.config.client.MkGameClientConfig.MK_GAME_CLIENT_NAME;

@Service
public class MkGameClient {

    public static final String GET_USER_INFO = "/user.info?handles={handle}";
    public static final String GET_USER_SUBMISSIONS = "/user.status?handle={handle}";
    public static final String GET_PROBLEM_SET = "/problemset.problems";

    private final WebClient webClient;

    public MkGameClient(@Qualifier(MK_GAME_CLIENT_NAME) WebClient webClient) {
        this.webClient = webClient;
    }

    public @NotNull Optional<CodeforcesUser> getUserInfo(@NotNull String handle){
        return webClient.get()
                .uri(GET_USER_INFO, handle)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CodeforcesResponseContainer<List<CodeforcesUser>>>() {
                })
                .blockOptional()
                .map(CodeforcesResponseContainer::getResult)
                .flatMap(users -> users.stream().findFirst());
    }

    public @NotNull List<Submission> getUserSubmissions(@NotNull String handle){
        return webClient.get()
                .uri(GET_USER_SUBMISSIONS, handle)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CodeforcesResponseContainer<List<Submission>>>() {})
                .blockOptional()
                .map(CodeforcesResponseContainer::getResult)
                .orElse(Collections.emptyList());
    }

    public @NotNull Optional<Problems> getProblemSet(){
        return webClient.get()
                .uri(GET_PROBLEM_SET)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CodeforcesResponseContainer<Problems>>() {})
                .blockOptional()
                .map(CodeforcesResponseContainer::getResult);
    }

}
