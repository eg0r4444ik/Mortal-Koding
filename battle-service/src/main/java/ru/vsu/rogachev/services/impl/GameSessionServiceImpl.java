package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.dto.GameInfoDTO;
import ru.vsu.rogachev.dto.GameStateDTO;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.entities.enums.GameState;
import ru.vsu.rogachev.repositories.GameSessionRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.PlayerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GameSessionServiceImpl implements GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private PlayerService playerService;

    @Override
    public void add(GameSession gameSession){
        gameSessionRepository.save(gameSession);
    }

    @Override
    public void add(Long time, Long playersCount, Long tasksCount) {
        GameSession gameSession = new GameSession(time, playersCount, tasksCount);
        gameSessionRepository.save(gameSession);
    }

    @Override
    public void addActivePlayer(GameSession game, Player player){
        game.addPlayer(player);
        playerService.setGameToActivePlayer(player, game);
        gameSessionRepository.save(game);
    }

    @Override
    public void addNotActivePlayer(GameSession game, Player player) {
        game.addPlayer(player);
        playerService.setGameToNotActivePlayer(player, game);
        gameSessionRepository.save(game);
    }

    @Override
    public void addTasks(GameSession game, List<Task> tasks) {
        game.addTasks(tasks);
        gameSessionRepository.save(game);
    }

    @Override
    public List<GameSession> getAll() {
        return gameSessionRepository.findAll();
    }

    @Override
    public GameSession getById(Long id){
        return gameSessionRepository.getById(id);
    }

    @Override
    public void startGame(Long id) {
        GameSession game = gameSessionRepository.getById(id);
        game.setState(GameState.IN_PROGRESS);
        game.setStartTime(new Date());
        gameSessionRepository.save(game);
    }

    @Override
    public void stopGame(Long id) {
        GameSession game = gameSessionRepository.getById(id);
        game.setState(GameState.FINISHED);
        gameSessionRepository.save(game);
    }

    @Transactional
    @Override
    public void deleteById(Long id){
        gameSessionRepository.deleteById(id);
    }

    @Override
    public GameStateDTO convertToState(GameSession game) {
        List<List<Long>> points = new ArrayList<>();

        for(Player player : game.getPlayers()){
            List<Long> playerPoints = new ArrayList<>();
            long point = 100;
            for(Task task : game.getTasks()){
                if(task.getSolver() != null && task.getSolver().getHandle().equals(player.getHandle())){
                    playerPoints.add(point);
                }else{
                    playerPoints.add(0L);
                }
                point += 100;
            }

            points.add(playerPoints);
        }

        GameStateDTO gameStateDTO = new GameStateDTO(points, getHandles(game.getPlayers()));
        return gameStateDTO;
    }

    @Override
    public GameInfoDTO convertToInfo(GameSession game, String message) {
        return new GameInfoDTO(message, getHandles(game.getPlayers()));
    }

    private List<String> getHandles(List<Player> players){
        List<String> handles = new ArrayList<>();
        for(Player player : players){
            handles.add(player.getHandle());
        }
        return handles;
    }
}
