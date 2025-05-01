package ru.vsu.rogachev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vsu.rogachev.config.db.EnableDb;

@EnableDb
@SpringBootApplication
public class MkGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(MkGameApplication.class);
    }

}