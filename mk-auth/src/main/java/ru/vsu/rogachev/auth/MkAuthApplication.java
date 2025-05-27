package ru.vsu.rogachev.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MkAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MkAuthApplication.class);
    }

}
