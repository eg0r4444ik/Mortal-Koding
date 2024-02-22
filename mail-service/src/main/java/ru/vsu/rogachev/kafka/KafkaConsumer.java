package ru.vsu.rogachev.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private KafkaDistributor distributor;

    @KafkaListener(topics = "mail-jpa", groupId = "mail-jpa-consumer")
    public void listenJpa(String message){
        distributor.jpaResponse(message);
    }

}
