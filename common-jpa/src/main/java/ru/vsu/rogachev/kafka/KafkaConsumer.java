package ru.vsu.rogachev.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private KafkaDistributor distributor;

    @KafkaListener(topics = "mail-jpa", groupId = "mail-jpa-consumer")
    public void listenMail(String message) throws JsonProcessingException {
        distributor.mailRequest(message);
    }

}
