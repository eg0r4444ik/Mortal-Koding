package ru.vsu.rogachev.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaDistributor {

    @Autowired
    private KafkaProducer producer;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void jpaRequest(){

    }

    public void jpaResponse(String response){

    }

}
