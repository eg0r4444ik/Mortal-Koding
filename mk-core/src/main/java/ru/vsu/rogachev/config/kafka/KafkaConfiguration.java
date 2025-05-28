package ru.vsu.rogachev.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic gameStateUpdateEventTopic(){
        return TopicBuilder.name("game-state-update-event-topic")
                .partitions(3)
                .replicas(2)
                .configs(Map.of("min.insync.replicas", "1"))
                .build();
    }

    @Bean
    public NewTopic gameEventTopic(){
        return TopicBuilder.name("game-event-topic")
                .partitions(3)
                .replicas(2)
                .configs(Map.of("min.insync.replicas", "1"))
                .build();
    }

}
