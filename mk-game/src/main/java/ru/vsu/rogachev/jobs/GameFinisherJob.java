package ru.vsu.rogachev.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.mk.game.dto.async.GameStateUpdateEvent;
import ru.vsu.rogachev.client.mk.game.dto.async.enums.GameStateUpdateEventType;
import ru.vsu.rogachev.entity.Game;
import ru.vsu.rogachev.service.GameService;
import ru.vsu.rogachev.utils.GameStateUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static ru.vsu.rogachev.entity.enums.GameState.FINISHED;
import static ru.vsu.rogachev.entity.enums.GameState.IN_PROGRESS;

@Log4j
@Service
@RequiredArgsConstructor
public class GameFinisherJob {

    private final GameService gameService;

    private final KafkaTemplate<String, GameStateUpdateEvent> gameStateUpdateEventKafkaTemplate;

    @Scheduled(fixedRate = 10000)
    public void checkForUpdate() {
        List<Game> games = gameService.getAllByState(IN_PROGRESS);

        for(Game game : games){
            Duration gameDuration = Duration.between(LocalDateTime.now(), game.getStartTime());
            if(gameDuration.compareTo(game.getParameters().getDuration()) >= 0){
                game.setState(FINISHED);
                gameService.save(game);
                gameStateUpdateEventKafkaTemplate.send("game-state-update-event-topic",
                        GameStateUtils.buildGameStateUpdateEvent(game, GameStateUpdateEventType.END_GAME));
            }
        }
    }
}
