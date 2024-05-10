package ru.vsu.rogachev.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.dto.GameInfoDTO;
import ru.vsu.rogachev.dto.enums.InfoType;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.services.GameSessionService;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, GameInfoDTO> stateKafkaTemplate;

    @Autowired
    private GameSessionService gameSessionService;

    public void sendGameInfo(GameSession game, InfoType type){
        stateKafkaTemplate.send("change-game-state-event-topic", gameSessionService.convertToInfo(game, type));
    }

}
