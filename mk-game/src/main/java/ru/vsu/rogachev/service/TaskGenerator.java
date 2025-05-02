package ru.vsu.rogachev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.codeforces.CodeforcesClient;
import ru.vsu.rogachev.client.codeforces.dto.Problems;
import ru.vsu.rogachev.client.codeforces.dto.Submission;
import ru.vsu.rogachev.entity.Player;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.utils.TaskUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.exception.BusinessLogicExceptions.CODEFORCES_NOT_AVAILABLE;

@Service
@RequiredArgsConstructor
public class TaskGenerator {

    private static final Integer TASK_RATING_STEP = 100;
    private static final Integer MIN_TASK_RATING = 800;
    private static final Integer TASK_DIFF = 200;

    private final CodeforcesClient codeforcesClient;

    @NotNull
    public List<String> getContestProblems(@NotNull List<Player> players, @NotNull Long tasksCount) {
        List<String> result = new ArrayList<>();

        List<Problems.Problem> problems = codeforcesClient.getProblemSet()
                .orElseThrow(() -> BusinessLogicException.of(CODEFORCES_NOT_AVAILABLE))
                .getProblems();
        Collections.shuffle(problems);

        List<Long> ratings = new ArrayList<>();
        for(Player player : players){
            ratings.add(player.getRating());
        }
        ratings.sort(Comparator.naturalOrder());

        long startTaskRating =
                Math.max(ratings.get(ratings.size()/2) - tasksCount * TASK_RATING_STEP - TASK_DIFF, MIN_TASK_RATING);

        Set<String> used = new HashSet<>();
        for(Player player : players){
            used.addAll(
                    codeforcesClient.getPlayersSubmissions(player.getHandle())
                            .stream()
                            .map(Submission::getProblem)
                            .map(TaskUtils::getProblemUrl)
                            .collect(Collectors.toSet())
            );
        }

        long currRating = startTaskRating;
        while(result.size() < tasksCount) {
            for (Problems.Problem problem : problems) {
                if (result.size() == tasksCount) {
                    break;
                }

                String problemUrl = TaskUtils.getProblemUrl(problem);
                if (problem.getRating() >= currRating && !used.contains(problemUrl)) {
                    result.add(problemUrl);
                    currRating = problem.getRating() + TASK_RATING_STEP;
                }
            }
        }

        return result;
    }

}