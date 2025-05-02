package ru.vsu.rogachev.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.mk.game.dto.async.GameEvent;
import ru.vsu.rogachev.processor.GameEventProcessor;

@Log4j
@Service
@RequiredArgsConstructor
public class GameEventConsumer {

    private final GameEventProcessor processor;

    @KafkaListener(topics = "game-event-topic", groupId = "game-events")
    public void processGameEvent(GameEvent event){
        processor.process(event);
    }

}
