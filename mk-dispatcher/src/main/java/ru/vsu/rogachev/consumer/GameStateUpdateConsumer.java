package ru.vsu.rogachev.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.mk.game.dto.async.GameStateUpdateEvent;

@Log4j
@Service
@RequiredArgsConstructor
public class GameStateUpdateConsumer {

    @KafkaListener(topics = "game-state-update-event-topic", groupId = "dispatcher-events")
    public void processGameStateUpdateEvent(GameStateUpdateEvent event){

    }

}
