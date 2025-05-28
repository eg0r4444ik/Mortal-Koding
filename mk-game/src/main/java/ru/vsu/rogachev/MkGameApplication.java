package ru.vsu.rogachev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.vsu.rogachev.config.kafka.EnableKafka;

@EnableKafka
@EnableScheduling
@EnableJpaRepositories
@SpringBootApplication
public class MkGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(MkGameApplication.class);
    }

}