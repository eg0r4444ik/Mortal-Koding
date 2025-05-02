package ru.vsu.rogachev.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.mk.game.dto.async.GameStateUpdateEvent;
import ru.vsu.rogachev.client.mk.game.dto.async.enums.GameStateUpdateEventType;
import ru.vsu.rogachev.entity.Game;
import ru.vsu.rogachev.entity.enums.PlayerState;
import ru.vsu.rogachev.service.GameService;
import ru.vsu.rogachev.utils.GameStateUtils;

import java.util.List;

import static ru.vsu.rogachev.entity.enums.GameState.IN_PROGRESS;
import static ru.vsu.rogachev.entity.enums.GameState.NOT_STARTED;

@Log4j
@Service
@RequiredArgsConstructor
public class GameStarterJob {

    private final GameService gameService;

    private final KafkaTemplate<String, GameStateUpdateEvent> gameStateUpdateEventKafkaTemplate;

    @Scheduled(fixedRate = 10000)
    public void checkForUpdate() {
        List<Game> games = gameService.getAllByState(NOT_STARTED);

        for(Game game : games){
            long activePlayersCount = game.getPlayers().stream()
                    .filter(player -> player.getState() == PlayerState.ACTIVE)
                    .count();
            if (activePlayersCount == game.getParameters().getPlayersCount()) {
                game.setState(IN_PROGRESS);
                gameService.save(game);
                gameStateUpdateEventKafkaTemplate.send("game-state-update-event-topic",
                        GameStateUtils.buildGameStateUpdateEvent(game, GameStateUpdateEventType.START_GAME));
            }
        }
    }

}
