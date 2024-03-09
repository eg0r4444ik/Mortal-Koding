package ru.vsu.rogachev;

import ru.vsu.rogachev.dto.ProblemDTO;
import ru.vsu.rogachev.generator.TaskGenerator;
import ru.vsu.rogachev.services.impl.ProblemServiceImpl;
import ru.vsu.rogachev.services.impl.PlayerServiceImpl;

import java.util.List;

public class Game {

    private TaskGenerator taskGenerator = new TaskGenerator();
    private PlayerServiceImpl userService = new PlayerServiceImpl();
    private ProblemServiceImpl problemService = new ProblemServiceImpl();

    public void start(String player1, String player2, long count) throws InterruptedException {
//        List<ProblemDTO> problems = taskGenerator.getContestProblems(player1, player2, count);
//        List<String> urls = problemService.getProblemSet(problems);
//        for(String url : urls){
//            System.out.println(url);
//        }

//        GameSession session = new GameSession(userService.getUser(player1), userService.getUser(player2), problems);
//        session.start();
    }

}
