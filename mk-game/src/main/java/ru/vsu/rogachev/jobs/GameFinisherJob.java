package ru.vsu.rogachev.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.codeforces.CodeforcesClient;
import ru.vsu.rogachev.client.codeforces.dto.Submission;
import ru.vsu.rogachev.client.codeforces.dto.enums.SubmissionVerdict;
import ru.vsu.rogachev.client.mk.game.dto.async.GameStateUpdateEvent;
import ru.vsu.rogachev.client.mk.game.dto.async.enums.GameStateUpdateEventType;
import ru.vsu.rogachev.entity.Game;
import ru.vsu.rogachev.entity.Player;
import ru.vsu.rogachev.entity.Task;
import ru.vsu.rogachev.service.GameService;
import ru.vsu.rogachev.utils.GameStateUtils;
import ru.vsu.rogachev.utils.TaskUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.vsu.rogachev.entity.enums.GameState.FINISHED;
import static ru.vsu.rogachev.entity.enums.GameState.IN_PROGRESS;

@Log4j
@Service
@RequiredArgsConstructor
public class GameFinisherJob {

    private final GameService gameService;

    private final CodeforcesClient codeforcesClient;

    private final KafkaTemplate<String, GameStateUpdateEvent> gameStateUpdateEventKafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void checkForUpdate() {
        List<Game> games = gameService.getAllByState(IN_PROGRESS);

        for(Game game : games){
            Duration gameDuration = Duration.between(Objects.requireNonNull(game.getStartTime()), LocalDateTime.now());
            if(
                    (gameDuration.compareTo(game.getParameters().getDuration()) >= 0
                            || isAllTasksSolved(game))
                            && !hasTestingTasks(game)
            ){
                game.setState(FINISHED);
                gameService.save(game);
                gameStateUpdateEventKafkaTemplate.send("game-state-update-event-topic",
                        GameStateUtils.buildGameStateUpdateEvent(game, GameStateUpdateEventType.END_GAME));
            }
        }
    }

    private boolean isAllTasksSolved(@NotNull Game game) {
        for (Task task : game.getTasks()) {
            if (task.getSolver() == null) {
                return false;
            }
        }

        return true;
    }

    private boolean hasTestingTasks(@NotNull Game game) {
        for (Player player : game.getPlayers()) {
            List<Submission> submissions = codeforcesClient.getPlayersSubmissions(player.getHandle());
            Map<String, Task> urls = new HashMap<>();
            for(Task task : game.getTasks()){
                urls.put(task.getTaskUrl(), task);
            }

            for(Submission submission : submissions){
                String url = TaskUtils.getProblemUrl(submission.getProblem());
                LocalDateTime submissionTime = LocalDateTime
                        .ofEpochSecond(submission.getCreationTimeSeconds(), 0, ZoneOffset.UTC)
                        .plusHours(3);

                if (!urls.containsKey(url)) continue;

                Task task = urls.get(url);
                if(submissionTime.isAfter(game.getStartTime())
                        && submissionTime.isBefore(Objects.requireNonNull(
                        game.getStartTime()).plus(game.getParameters().getDuration()))
                        && submission.getVerdict() == SubmissionVerdict.TESTING
                        && (task.getSolver() == null || submissionTime.isBefore(task.getSolveTime()))
                ){
                    return true;
                }
            }
        }

        return false;
    }

}
