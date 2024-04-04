package ru.vsu.rogachev.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.codeforces.CodeforcesConnection;
import ru.vsu.rogachev.dto.SubmissionDTO;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.entities.enums.GameState;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.ProblemService;
import ru.vsu.rogachev.services.SubmissionService;
import ru.vsu.rogachev.services.TaskService;

import java.util.*;

@Log4j
@Service
public class Scheduler {

    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private TaskService taskService;

    private final int MILLISECONDS_COEFF = 1000;

    @Scheduled(fixedRate = 5000)
    public void checkForUpdate() throws JsonProcessingException, InterruptedException {
        List<GameSession> games = gameSessionService.getAll();

        for(GameSession game : games){
            if(game.getState() == GameState.IN_PROGRESS){
                if(new Date().getTime() - game.getStartTime().getTime() >= game.getTime()){
                    gameSessionService.stopGame(game.getId());
                }else {
                    for (Player player : game.getPlayers()) {
                        updatePlayerSubmissions(player, game);
                    }
                }
            }
        }
    }

    private void updatePlayerSubmissions(Player player, GameSession game) throws InterruptedException, JsonProcessingException {
        List<SubmissionDTO> submissions = submissionService.getPlayerSubmissions(player.getHandle());
        Map<String, Task> urls = new HashMap<>();
        for(Task task : game.getTasks()){
            urls.put(task.getTaskUrl(), task);
        }

        for(SubmissionDTO submission : submissions){
            Date date = new Date(submission.getCreationTimeSeconds()*MILLISECONDS_COEFF);
            String url = problemService.getProblemUrl(submission.getProblem());
            if(date.after(game.getStartTime()) && submission.getVerdict() == SubmissionDTO.Verdict.OK
                    && urls.containsKey(url) && (urls.get(url).getSolver() == null || date.before(urls.get(url).getTime()))){
                Task task = urls.get(url);
                taskService.setSolver(task, player);
            }
        }
    }

}
