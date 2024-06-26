package ru.vsu.rogachev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodeforcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeforcesApplication.class);
    }

}
