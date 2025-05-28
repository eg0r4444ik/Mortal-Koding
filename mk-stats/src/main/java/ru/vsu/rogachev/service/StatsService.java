package ru.vsu.rogachev.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.mk.container.ResponseContainer;
import ru.vsu.rogachev.client.mk.stats.dto.UserStatisticResponse;
import ru.vsu.rogachev.entity.GameLog;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final GameLogService gameLogService;

    public ResponseContainer<UserStatisticResponse> getUserStatistic(@NotNull String handle) {
        List<UserStatisticResponse.GameResult> userGameResults = gameLogService.getAllByPlayersHandle(handle)
                .stream()
                .sorted(Comparator.comparing(GameLog::getEndTime))
                .map(this::mapGameLogToResult)
                .toList();

        return ResponseContainer.success(new UserStatisticResponse(userGameResults));
    }

    @NotNull
    private UserStatisticResponse.GameResult mapGameLogToResult(@NotNull GameLog gameLog) {
        return new UserStatisticResponse.GameResult(
                gameLog.getEndTime(),
                gameLog.getPlace(),
                gameLog.getGameScore(),
                gameLog.getOldRating(),
                gameLog.getNewRating(),
                gameLog.getPerformance()
        );
    }

}
