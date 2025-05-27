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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class GameStateUtils {

    private static final Long TASK_POINTS_COEFFICIENT = 100L;
    private static final int K = 32;

    @NotNull
    public GameStateUpdateEvent buildGameStateUpdateEvent(@NotNull Game game, @NotNull GameStateUpdateEventType eventType) {
        CurrentGameState gameState = buildCurrentGameState(game);
        return new GameStateUpdateEvent(gameState, eventType, recalculatePlayersRating(game));
    }

    @NotNull
    public GetGameStateResponse buildGameStateResponse(@NotNull Game game) {
        CurrentGameState gameState = buildCurrentGameState(game);
        return new GetGameStateResponse(gameState);
    }

    @NotNull
    private CurrentGameState buildCurrentGameState(@NotNull Game game) {
        List<String> tasks = game.getTasks()
                .stream()
                .map(Task::getTaskUrl)
                .toList();
        List<CurrentGameState.PlayersScore> scores = new ArrayList<>();

        for (Player player : game.getPlayers()) {
            for (Task task : game.getTasks()) {
                Long score = Objects.equals(task.getSolver(), player) ? task.getSerialNumber() * TASK_POINTS_COEFFICIENT : 0L;
                scores.add(new CurrentGameState.PlayersScore(task.getSerialNumber(), player.getHandle(), score));
            }
        }

        return new CurrentGameState(scores, tasks, Duration.between(LocalDateTime.now(), game.getStartTime()));
    }

    @NotNull
    private List<GameStateUpdateEvent.PlayersRating> recalculatePlayersRating(@NotNull Game game) {
        Map<Player, Long> playersScore = new HashMap<>();
        Map<Player, Double> oldRatings = new HashMap<>();

        // Подсчёт очков
        for (Player player : game.getPlayers()) {
            long score = 0;
            for (Task task : game.getTasks()) {
                if (Objects.equals(task.getSolver(), player)) {
                    score += task.getSerialNumber() * TASK_POINTS_COEFFICIENT;
                }
            }
            playersScore.put(player, score);
            oldRatings.put(player, player.getRating().doubleValue());
        }

        // Сортировка игроков по набранным очкам (чем выше — тем лучше место)
        List<Player> sortedPlayers = playersScore.entrySet().stream()
                .sorted(Map.Entry.<Player, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        Map<Player, Double> newRatings = new HashMap<>();

        for (Player player : sortedPlayers) {
            double rating = oldRatings.get(player);
            double expectedScoreSum = 0.0;
            double actualScoreSum = 0.0;

            for (Player opponent : sortedPlayers) {
                if (opponent == player) continue;

                double playerRating = oldRatings.get(player);
                double opponentRating = oldRatings.get(opponent);

                // Ожидаемый результат игрока против оппонента
                double expectedScore = 1.0 / (1.0 + Math.pow(10, (opponentRating - playerRating) / 400.0));
                expectedScoreSum += expectedScore;

                // Фактический результат: победа = 1, проигрыш = 0, ничья = 0.5
                int playerPlace = sortedPlayers.indexOf(player);
                int opponentPlace = sortedPlayers.indexOf(opponent);

                double actualScore;
                if (playerPlace < opponentPlace) actualScore = 1.0;
                else if (playerPlace == opponentPlace) actualScore = 0.5;
                else actualScore = 0.0;

                actualScoreSum += actualScore;
            }

            // Обновлённый рейтинг игрока
            double newRating = rating + K * (actualScoreSum - expectedScoreSum);
            newRatings.put(player, newRating);
        }

        // Преобразуем в результат
        return sortedPlayers.stream()
                .map(player -> new GameStateUpdateEvent.PlayersRating(
                        player.getHandle(),
                        player.getRating(),
                        Math.round(newRatings.get(player))
                ))
                .toList();
    }


}
