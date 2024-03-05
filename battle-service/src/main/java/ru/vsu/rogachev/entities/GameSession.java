package ru.vsu.rogachev.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class GameSession extends Thread{

    public enum TaskCondition{
        DRAW,
        PLAYER1,
        PLAYER2
    }

    private User user1, user2;
    List<Problem> problems;
    List<TaskCondition> conditions;
    long timeStart;

    public GameSession(User user1, User user2, List<Problem> problems) {
        this.user1 = user1;
        this.user2 = user2;
        this.problems = problems;

        conditions = new ArrayList<>();
        for(Problem problem : problems){
            conditions.add(TaskCondition.DRAW);
        }

        this.timeStart = System.currentTimeMillis();
    }

    @SneakyThrows
    public void run(){
        long currTime = System.currentTimeMillis();
        while(currTime - timeStart < 60000){
            Thread.sleep(1000);
            System.out.println(user1.getHandle());
            currTime = System.currentTimeMillis();
        }
    }

}
