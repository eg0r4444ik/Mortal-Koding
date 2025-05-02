package ru.vsu.rogachev.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import ru.vsu.rogachev.client.mk.game.dto.CurrentGameState;
import ru.vsu.rogachev.client.mk.game.dto.async.GameStateUpdateEvent;
import ru.vsu.rogachev.client.mk.game.dto.async.enums.GameStateUpdateEventType;
import ru.vsu.rogachev.client.mk.game.dto.rest.GetGameStateResponse;
import ru.vsu.rogachev.entity.Game;
import ru.vsu.rogachev.entity.Player;
import ru.vsu.rogachev.entity.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class GameStateUtils {

    private static final Long TASK_POINTS_COEFFICIENT = 100L;

    @NotNull
    public GameStateUpdateEvent buildGameStateUpdateEvent(@NotNull Game game, @NotNull GameStateUpdateEventType eventType) {
        CurrentGameState gameState = buildCurrentGameState(game);
        return new GameStateUpdateEvent(gameState, eventType);
    }

    @NotNull
    public GetGameStateResponse buildGameStateResponse(@NotNull Game game) {
        CurrentGameState gameState = buildCurrentGameState(game);
        return new GetGameStateResponse(gameState);
    }

    @NotNull
    private CurrentGameState buildCurrentGameState(@NotNull Game game) {
        List<CurrentGameState.PlayersScore> scores = new ArrayList<>();

        for (Player player : game.getPlayers()) {
            for (Task task : game.getTasks()) {
                Long score = Objects.equals(task.getSolver(), player) ? task.getSerialNumber() * TASK_POINTS_COEFFICIENT : 0L;
                scores.add(new CurrentGameState.PlayersScore(task.getSerialNumber(), player.getHandle(), score));
            }
        }

        return new CurrentGameState(scores, Duration.between(LocalDateTime.now(), game.getStartTime()));
    }

}
