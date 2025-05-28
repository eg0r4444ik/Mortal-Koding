package ru.vsu.rogachev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.vsu.rogachev.config.client.codeforces.EnableCodeforcesClient;
import ru.vsu.rogachev.config.kafka.EnableKafka;

@EnableKafka
@EnableScheduling
@SpringBootApplication
@EnableCodeforcesClient
public class MkDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MkDispatcherApplication.class);
    }

}
