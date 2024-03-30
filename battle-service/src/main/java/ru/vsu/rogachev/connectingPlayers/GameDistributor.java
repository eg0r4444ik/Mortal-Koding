package ru.vsu.rogachev.connectingPlayers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.codeforces.CodeforcesConnection;
import ru.vsu.rogachev.dto.PlayerDTO;
import ru.vsu.rogachev.dto.ProblemDTO;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.entities.enums.PlayerState;
import ru.vsu.rogachev.generator.TaskGenerator;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.PlayerService;
import ru.vsu.rogachev.services.TaskService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GameDistributor {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskGenerator taskGenerator;

    public void connectPlayerToGame(String handle, long playersCount, long time, long tasksCount) throws JsonProcessingException, InterruptedException {
        Player player = playerService.getPlayer(handle);
        playerService.add(player);

        List<GameSession> games = gameSessionService.getAll();

        for(GameSession game : games){
            if(game.getPlayersCount() == playersCount && game.getTime() == time && !game.isStarted() &&
                    game.getPlayersCount() != game.getPlayers().size() && !haveNotActivePlayers(game)){
                gameSessionService.addActivePlayer(game, player);
                return;
            }
        }

        GameSession game = new GameSession(time, playersCount, tasksCount);
        gameSessionService.add(game);
        gameSessionService.addActivePlayer(game, player);
    }

    public void createGameWithPlayers(String handle, long playersCount, long time, long tasksCount, List<String> handles) throws InterruptedException, JsonProcessingException {
        Player player = playerService.getPlayer(handle);
        playerService.add(player);

        List<Player> players = new ArrayList<>();
        for(String hand : handles){
            Player p = playerService.getPlayer(hand);
            players.add(p);
            playerService.add(p);
        }

        GameSession game = new GameSession(time, playersCount, tasksCount);
        gameSessionService.add(game);
        gameSessionService.addActivePlayer(game, player);

        for(Player p : players){
            gameSessionService.addNotActivePlayer(game, p);
        }
    }

    public void startGame(Long gameId) throws InterruptedException, JsonProcessingException {
        GameSession game= gameSessionService.getById(gameId);
        game.setStarted(true);
        game.setStartTime(new Date());

        List<String> problems = taskGenerator.getContestProblems(game.getPlayers(), game.getTasksCount());
        List<Task> tasks = new ArrayList<>();
        for(String url : problems){
            Task task = new Task(game, url);
            taskService.add(task);
        }

        gameSessionService.addTasks(game, tasks);
    }

    private boolean haveNotActivePlayers(GameSession gameSession){
        for(Player player : gameSession.getPlayers()){
            if(player.getState() == PlayerState.NOT_CONNECTED){
                return true;
            }
        }

        return false;
    }

}
