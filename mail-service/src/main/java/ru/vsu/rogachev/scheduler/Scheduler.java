package ru.vsu.rogachev.scheduler;

import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class Scheduler {

    @Scheduled(fixedRate = 5000)
    public void print(){
        System.out.println("Hi");
    }

}
