package ru.vsu.rogachev.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendToJpa(String message){
        kafkaTemplate.send("mail-jpa", message);
    }

}
