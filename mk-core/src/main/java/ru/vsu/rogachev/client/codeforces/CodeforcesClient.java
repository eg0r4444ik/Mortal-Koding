package ru.vsu.rogachev.client.codeforces;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vsu.rogachev.client.codeforces.entity.Problems;
import ru.vsu.rogachev.client.codeforces.entity.CodeforcesResponseContainer;
import ru.vsu.rogachev.client.codeforces.entity.Submission;
import ru.vsu.rogachev.client.codeforces.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.vsu.rogachev.config.CodeforcesClientConfig.CODEFORCES_CLIENT;

@RequiredArgsConstructor
@Service
public class CodeforcesClient {

    public static final String GET_USER_INFO = "/user.info?handles={handle}";
    public static final String GET_USER_SUBMISSIONS = "/user.status?handle={handle}";
    public static final String GET_PROBLEM_SET = "/problemset.problems";

    private WebClient webClient;

    public CodeforcesClient(@Qualifier(CODEFORCES_CLIENT) WebClient webClient) {
        this.webClient = webClient;
    }

    public @NotNull Optional<User> getUserInfo(@NotNull String handle){
        return webClient.get()
                .uri(GET_USER_INFO, handle)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CodeforcesResponseContainer<List<User>>>() {
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
