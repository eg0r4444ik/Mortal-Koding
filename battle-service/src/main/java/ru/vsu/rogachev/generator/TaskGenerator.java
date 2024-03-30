package ru.vsu.rogachev.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.dto.PlayerDTO;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.dto.ProblemDTO;
import ru.vsu.rogachev.services.impl.ProblemServiceImpl;
import ru.vsu.rogachev.services.impl.PlayerServiceImpl;

import java.util.*;

@Service
public class TaskGenerator {

    @Autowired
    private PlayerServiceImpl playerService;

    @Autowired
    private ProblemServiceImpl problemService;

    static final int TASK_RATING_STEP = 100;

    static final int MIN_TASK_RATING = 800;

    static final int TASK_DIFF = 200;

    public List<String> getContestProblems(List<Player> players, long tasksCount) throws InterruptedException, JsonProcessingException {
        List<String> result = new ArrayList<>();
        List<ProblemDTO> problems = problemService.getProblems();
        problems.sort(Comparator.naturalOrder());

        List<Long> ratings = new ArrayList<>();
        for(Player player : players){
            ratings.add(player.getRating());
        }
        ratings.sort(Comparator.naturalOrder());
        long startTaskRating = Math.max(ratings.get(ratings.size()/2) - tasksCount*TASK_RATING_STEP - TASK_DIFF, MIN_TASK_RATING);

        Set<String> used = new HashSet<>();
        for(Player player : players){
            Set<String> playerProblems = playerService.getPlayerProblemSet(player.getHandle());
            used.addAll(playerProblems);
        }

        long currRating = startTaskRating;
        for(ProblemDTO problem : problems){
            if(result.size() == tasksCount){
                break;
            }

            String problemUrl = problemService.getProblemUrl(problem);
            if(problem.getRating() >= currRating && !used.contains(problemUrl)){
                result.add(problemUrl);
                currRating = problem.getRating() + 100;
            }
        }

        return result;
    }

}
