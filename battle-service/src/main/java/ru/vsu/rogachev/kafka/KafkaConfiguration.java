package ru.vsu.rogachev.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic changeGameState(){
        return TopicBuilder.name("change-game-state-event-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "1"))
                .build();
    }

    @Bean
    public NewTopic sendMessageTopic(){
        return TopicBuilder.name("send-message-event-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "1"))
                .build();
    }

    @Bean
    public NewTopic startGame(){
        return TopicBuilder.name("start-game-event-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "1"))
                .build();
    }

}
