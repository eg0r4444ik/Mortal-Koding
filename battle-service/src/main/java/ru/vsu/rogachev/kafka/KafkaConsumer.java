package ru.vsu.rogachev.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.distributor.GameDistributor;
import ru.vsu.rogachev.dto.GameDTO;
import ru.vsu.rogachev.entities.GameSession;

@Service
public class KafkaConsumer {

    @Autowired
    private GameDistributor gameDistributor;

    @KafkaListener(topics = "start-game-event-topic", groupId = "battle-events")
    public void startGame(GameDTO game){
        try {
            gameDistributor.createGameWithPlayers(game.getHandle(), game.getHandles().get(0), game.getTime(), game.getTasksCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
