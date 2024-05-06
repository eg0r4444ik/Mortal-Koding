package ru.vsu.rogachev.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import ru.vsu.rogachev.dto.GameDTO;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, GameDTO> gameDTOKafkaTemplate;

    public void sendEmail(String email){
        stringKafkaTemplate.send("send-message-event-topic", email);
    }

    public void startGame(GameDTO gameDTO){
        gameDTOKafkaTemplate.send("start-game-event-topic", gameDTO);
    }

}
