package ru.vsu.rogachev.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.mk.game.dto.CurrentGameState;
import ru.vsu.rogachev.client.mk.game.dto.async.GameStateUpdateEvent;
import ru.vsu.rogachev.client.mk.game.dto.async.enums.GameStateUpdateEventType;
import ru.vsu.rogachev.entity.GameLog;
import ru.vsu.rogachev.service.GameLogService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameStateUpdateProcessor {

    private final GameLogService gameLogService;

    public void process(GameStateUpdateEvent event) {
        if ( event.getEventType() != GameStateUpdateEventType.END_GAME) return;

        Map<String, Long> totalScores = event.getState().getPlayersScores().stream()
                .collect(Collectors.groupingBy(
                        CurrentGameState.PlayersScore::getPlayerHandle,
                        Collectors.summingLong(CurrentGameState.PlayersScore::getScore)
                ));

        List<Map.Entry<String, Long>> sortedByScore = totalScores.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .toList();

        Map<String, Integer> places = new HashMap<>();
        int place = 1;
        for (int i = 0; i < sortedByScore.size(); i++) {
            String handle = sortedByScore.get(i).getKey();
            if (i > 0 && sortedByScore.get(i).getValue().equals(sortedByScore.get(i - 1).getValue())) {
                places.put(handle, places.get(sortedByScore.get(i - 1).getKey()));
            } else {
                places.put(handle, place);
            }
            place++;
        }

        Map<String, GameStateUpdateEvent.PlayersRating> ratings = event.getRecalculatedRatings().stream()
                .collect(Collectors.toMap(
                        GameStateUpdateEvent.PlayersRating::getPlayerHandle,
                        Function.identity()
                ));

        for (String handle : totalScores.keySet()) {
            GameStateUpdateEvent.PlayersRating rating = ratings.get(handle);
            Integer playerPlace = places.get(handle);
            long performance = calculatePerformance(handle, playerPlace, places, ratings, totalScores);

            GameLog gameLog = new GameLog(
                    LocalDateTime.now(),
                    handle,
                    totalScores.get(handle),
                    playerPlace,
                    rating.getOldRating(),
                    rating.getNewRating(),
                    performance
            );
            gameLogService.save(gameLog);
        }
    }

    private Long calculatePerformance(
            String playerHandle,
            Integer place,
            Map<String, Integer> places,
            Map<String, GameStateUpdateEvent.PlayersRating> ratings,
            Map<String, Long> playerScores
    ) {
        int totalPlayers = places.size();
        int opponentsCount = totalPlayers - 1;

        long beatenPlayers = places.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(playerHandle) && entry.getValue() > place)
                .count();

        long tiedPlayers = places.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(playerHandle) && entry.getValue().equals(place))
                .count();

        long playerScore = playerScores.getOrDefault(playerHandle, 0L);
        long maxOpponentScore = playerScores.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(playerHandle))
                .mapToLong(Map.Entry::getValue)
                .max().orElse(0L);

        double scoreRatioBonus = 0.0;
        if (maxOpponentScore > 0) {
            scoreRatioBonus = 0.5 * (double) playerScore / maxOpponentScore;
        }

        // S_real в диапазоне [0, opponentsCount + 0.5 + 0.5] = [0, totalPlayers]
        double S_real = beatenPlayers + 0.5 * tiedPlayers + scoreRatioBonus;

        // Нормируем на число оппонентов
        S_real /= opponentsCount;

        List<Long> opponentsRatings = ratings.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(playerHandle))
                .map(entry -> entry.getValue().getOldRating())
                .toList();

        long low = 100L;
        long high = 3000L;
        long bestPerf = 1500L;
        double bestDiff = Double.MAX_VALUE;

        // Ограничение на рост performance — не должен быть выше rating + 50 при поражении
        long playerRating = ratings.get(playerHandle).getOldRating();
        if (S_real < 0.5) {
            high = Math.min(high, playerRating + 50);
        }

        while (low <= high) {
            long mid = (low + high) / 2;

            double expectedScore = 0;
            for (long oppRating : opponentsRatings) {
                expectedScore += 1.0 / (1.0 + Math.pow(10, (oppRating - mid) / 400.0));
            }

            expectedScore /= opponentsCount;

            double diff = Math.abs(expectedScore - S_real);
            if (diff < bestDiff) {
                bestDiff = diff;
                bestPerf = mid;
            }

            if (expectedScore > S_real) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return bestPerf;
    }

}
