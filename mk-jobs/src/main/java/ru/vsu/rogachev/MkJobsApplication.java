package ru.vsu.rogachev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vsu.rogachev.config.EnableDb;

@EnableDb
@SpringBootApplication
public class MkJobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MkJobsApplication.class);
    }

}