package ru.vsu.rogachev.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.dto.PlayerDTO;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.dto.ProblemDTO;
import ru.vsu.rogachev.services.impl.ProblemServiceImpl;
import ru.vsu.rogachev.services.impl.PlayerServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class TaskGenerator {

    @Autowired
    private PlayerServiceImpl userService;

    @Autowired
    private ProblemServiceImpl problemService;

    public List<ProblemDTO> getContestProblems(String player1, String player2, long count) throws InterruptedException, JsonProcessingException {
        List<ProblemDTO> result = new ArrayList<>();
        List<ProblemDTO> problems = problemService.getProblems();
        Set<String> user1Problems = userService.getPlayerProblemSet(player1);
        Set<String> user2Problems = userService.getPlayerProblemSet(player2);
        PlayerDTO user1 = userService.getPlayer(player1);
        PlayerDTO user2 = userService.getPlayer(player2);

        long startRating = Math.max(Math.min(user1.getRating(), user2.getRating()) - count*100 - 200, 800);
        problems.sort(Comparator.naturalOrder());

        long currRating = startRating;
        for(ProblemDTO problem : problems){
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
