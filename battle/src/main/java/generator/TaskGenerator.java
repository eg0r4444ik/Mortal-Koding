package generator;

import entities.Problem;
import entities.User;
import services.ProblemService;
import services.UserService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class TaskGenerator {

    private UserService userService = new UserService();
    private ProblemService problemService = new ProblemService();

    public List<Problem> getContestProblems(String player1, String player2, long count) throws InterruptedException {
        List<Problem> result = new ArrayList<>();
        List<Problem> problems = problemService.getProblems();
        Set<String> user1Problems = userService.getUserProblemSet(player1);
        Set<String> user2Problems = userService.getUserProblemSet(player2);
        User user1 = userService.getUser(player1);
        User user2 = userService.getUser(player2);

        long startRating = Math.max(Math.min(user1.getRating(), user2.getRating()) - count*100 - 200, 800);
        problems.sort(Comparator.naturalOrder());

        long currRating = startRating;
        for(Problem problem : problems){
            if(result.size() == count){
                break;
            }

            String problemUrl = problemService.getProblemUrl(problem);
            if(problem.getRating() >= currRating && !user1Problems.contains(problemUrl) && !user2Problems.contains(problemUrl)){
                result.add(problem);
                currRating = problem.getRating() + 100;
            }
        }

        return result;
    }

}
