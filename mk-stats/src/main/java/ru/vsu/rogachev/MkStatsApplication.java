package ru.vsu.rogachev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.vsu.rogachev.config.kafka.EnableKafka;

@EnableKafka
@EnableJpaRepositories
@SpringBootApplication
public class MkStatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MkStatsApplication.class);
    }

}