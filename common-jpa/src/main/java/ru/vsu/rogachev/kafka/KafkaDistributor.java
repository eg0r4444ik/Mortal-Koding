package ru.vsu.rogachev.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.kafka.enums.Operation;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.TaskService;
import ru.vsu.rogachev.services.UserService;

@Component
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

        switch (jpaRequest.getTable()){
            case USER:
                userOperations(jpaRequest);
                break;
            case GAME_SESSION:
                gameOperations(jpaRequest);
                break;
            case TASK:
                taskOperations(jpaRequest);
                break;
            case CONFIRM_REQUEST:
                confirmOperations(jpaRequest);
                break;
        }

        String response = "";
        producer.sendToMail(response);
    }

    private void userOperations(JpaKafkaRequest jpaKafkaRequest){
        switch (jpaKafkaRequest.getOperation()){
            case ADD:

                break;
            case GET:
                break;
            case DELETE:
                break;
            case CHANGE:
                break;
        }
    }

    private void gameOperations(JpaKafkaRequest jpaKafkaRequest){

    }

    private void taskOperations(JpaKafkaRequest jpaKafkaRequest){

    }

    private void confirmOperations(JpaKafkaRequest jpaKafkaRequest){

    }

}
