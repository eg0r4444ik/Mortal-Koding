package ru.vsu.rogachev.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic mailTopic(){
        return new NewTopic("mail-jpa", 1, (short) 1);
    }

}
