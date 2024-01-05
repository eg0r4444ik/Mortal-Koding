import entities.GameSession;
import entities.Problem;
import generator.TaskGenerator;
import services.ProblemService;
import services.UserService;

import java.util.List;

public class Game {

    private TaskGenerator taskGenerator = new TaskGenerator();
    private UserService userService = new UserService();
    private ProblemService problemService = new ProblemService();

    public void start(String player1, String player2, long count) throws InterruptedException {
        List<Problem> problems = taskGenerator.getContestProblems(player1, player2, count);
        List<String> urls = problemService.getProblemSet(problems);
        for(String url : urls){
            System.out.println(url);
        }

        GameSession session = new GameSession(userService.getUser(player1), userService.getUser(player2), problems);
        session.start();
    }

}
