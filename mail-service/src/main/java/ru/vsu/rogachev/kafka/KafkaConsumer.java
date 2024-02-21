package ru.vsu.rogachev.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "mail-jpa", groupId = "mail-jpa-consumer")
    public void listenJpa(String message){
        System.out.println(message);
    }

}
