package ru.vsu.rogachev.kafka;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.controllers.UpdateController;
import ru.vsu.rogachev.dto.GameInfoDTO;

@Service
@Log4j
public class KafkaConsumer {

    @Autowired
    private UpdateController updateController;

    @KafkaListener(topics = "change-game-state-event-topic", groupId = "dispatcher-events")
    public void printGameInfo(GameInfoDTO gameInfo){
        updateController.printGameInfo(gameInfo);
    }

}
