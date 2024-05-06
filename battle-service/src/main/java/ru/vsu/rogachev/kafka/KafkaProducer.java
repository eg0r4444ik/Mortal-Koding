package ru.vsu.rogachev.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.dto.GameInfoDTO;
import ru.vsu.rogachev.dto.GameStateDTO;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.services.GameSessionService;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, GameStateDTO> stateKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, GameInfoDTO> infoKafkaTemplate;

    @Autowired
    private GameSessionService gameSessionService;

    public void sendGameState(GameSession game){
        stateKafkaTemplate.send("change-game-state-event-topic", gameSessionService.convertToState(game));
    }

    public void sendMessage(GameSession game, String message){
        infoKafkaTemplate.send("send-game-info-event-topic", gameSessionService.convertToInfo(game, message));
    }

}
