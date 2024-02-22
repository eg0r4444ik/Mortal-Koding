package ru.vsu.rogachev.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.kafka.enums.Operation;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.TaskService;
import ru.vsu.rogachev.services.UserService;

@Service
public class KafkaDistributor {

    @Autowired
    private UserService userService;
    @Autowired
    private GameSessionService gameSessionService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ConfirmService confirmService;
    @Autowired
    private KafkaProducer producer;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void mailRequest(String request) throws JsonProcessingException {
        JpaKafkaRequest jpaRequest = objectMapper.readValue(request, JpaKafkaRequest.class);

        String response = "";
        producer.sendToMail(response);
    }

}
