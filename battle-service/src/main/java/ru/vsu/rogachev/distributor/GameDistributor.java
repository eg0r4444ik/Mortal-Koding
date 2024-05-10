package ru.vsu.rogachev.distributor;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.dto.enums.InfoType;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.entities.enums.GameState;
import ru.vsu.rogachev.entities.enums.PlayerState;
import ru.vsu.rogachev.generator.TaskGenerator;
import ru.vsu.rogachev.kafka.KafkaProducer;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.PlayerService;
import ru.vsu.rogachev.services.TaskService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GameDistributor {

    private final PlayerService playerService;

    private final GameSessionService gameSessionService;

    private final TaskService taskService;

    private final TaskGenerator taskGenerator;

    private final KafkaProducer kafkaProducer;

    public void connectPlayerToGame(String handle, Long gameId) throws JsonProcessingException, InterruptedException {
        Player player = playerService.getPlayer(handle);
        playerService.add(player);

        GameSession game = gameSessionService.getById(gameId);
        gameSessionService.addActivePlayer(game, player);
        startGame(game);
    }

    public void connectPlayerToGame(String handle, long playersCount, long time, long tasksCount) throws JsonProcessingException, InterruptedException {
        Player player = playerService.getPlayer(handle);
        playerService.add(player);

        List<GameSession> games = gameSessionService.getAll();

        for(GameSession game : games){
            if(game.getPlayersCount() == playersCount && game.getTime() == time && game.getState() == GameState.NOT_STARTED &&
                    game.getPlayersCount() != game.getPlayers().size() && !haveNotActivePlayers(game)){
                gameSessionService.addActivePlayer(game, player);
                startGame(game);
                return;
            }
        }

        GameSession game = new GameSession(time, playersCount, tasksCount, GameState.NOT_STARTED);
        gameSessionService.add(game);
        gameSessionService.addActivePlayer(game, player);
        startGame(game);
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

        GameSession game = new GameSession(time, playersCount, tasksCount, GameState.NOT_STARTED);
        gameSessionService.add(game);
        gameSessionService.addActivePlayer(game, player);

        for(Player p : players){
            gameSessionService.addNotActivePlayer(game, p);
        }
    }

    public void createGameWithPlayers(String handle1, String handle2, long time, long tasksCount) throws InterruptedException, JsonProcessingException {
        Player player1 = playerService.getPlayer(handle1);
        Player player2 = playerService.getPlayer(handle2);
        playerService.add(player1);
        playerService.add(player2);

        GameSession game = new GameSession(time, 2L, tasksCount, GameState.IN_PROGRESS);
        game.setStartTime(new Date());
        gameSessionService.add(game);
        gameSessionService.addActivePlayer(game, player1);
        gameSessionService.addActivePlayer(game, player2);

        List<String> problems = taskGenerator.getContestProblems(game.getPlayers(), game.getTasksCount());
        List<Task> tasks = new ArrayList<>();

        int num = 1;
        for (String url : problems) {
            Task task = new Task(game, url, num);
            tasks.add(task);
            taskService.add(task);
            num++;
        }

        gameSessionService.addTasks(game, tasks);
        kafkaProducer.sendGameInfo(game, InfoType.STARTED);
    }

    public void startGame(GameSession game) throws InterruptedException, JsonProcessingException {
        if(readyToStart(game)) {
            gameSessionService.startGame(game.getId());

            List<String> problems = taskGenerator.getContestProblems(game.getPlayers(), game.getTasksCount());
            List<Task> tasks = new ArrayList<>();

            int num = 1;
            for (String url : problems) {
                Task task = new Task(game, url, num);
                tasks.add(task);
                taskService.add(task);
                num++;
            }

            gameSessionService.addTasks(game, tasks);
            kafkaProducer.sendGameInfo(game, InfoType.STARTED);
        }
    }

    private boolean haveNotActivePlayers(GameSession game){
        for(Player player : game.getPlayers()){
            if(player.getState() == PlayerState.NOT_CONNECTED){
                return true;
            }
        }

        return false;
    }

    private boolean readyToStart(GameSession game){
        if(game.getState() == GameState.NOT_STARTED && game.getPlayers().size() == game.getPlayersCount() && !haveNotActivePlayers(game)){
            return true;
        }
        return false;
    }

}
