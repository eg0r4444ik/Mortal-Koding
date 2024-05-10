package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.dto.GameInfoDTO;
import ru.vsu.rogachev.dto.enums.InfoType;
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
    public void add(Long time, Long playersCount, Long tasksCount, GameState state) {
        GameSession gameSession = new GameSession(time, playersCount, tasksCount, state);
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
        return gameSessionRepository.findById(id).get();
    }

    @Override
    public void startGame(Long id) {
        GameSession game = gameSessionRepository.findById(id).get();
        game.setState(GameState.IN_PROGRESS);
        game.setStartTime(new Date());
        gameSessionRepository.save(game);
    }

    @Override
    public void stopGame(Long id) {
        GameSession game = gameSessionRepository.findById(id).get();
        game.setState(GameState.FINISHED);
        gameSessionRepository.save(game);
    }

    @Transactional
    @Override
    public void deleteById(Long id){
        gameSessionRepository.deleteById(id);
    }

    @Override
    public GameInfoDTO convertToInfo(GameSession game, InfoType type) {
        List<List<Long>> points = new ArrayList<>();

        List<String> urls = new ArrayList<>();
        for(Task task : game.getTasks()){
            urls.add(task.getTaskUrl());
        }

        for(Player player : game.getPlayers()){
            long[] playerPoints = new long[game.getTasks().size()];
            for(Task task : game.getTasks()){
                if(task.getSolver() != null && task.getSolver().getHandle().equals(player.getHandle())){
                    playerPoints[task.getNumberInGame()-1] = (long)100*task.getNumberInGame();
                }else{
                    playerPoints[task.getNumberInGame()-1] = 0;
                }
            }

            List<Long> playerPointsList = new ArrayList<>();
            for(long point : playerPoints){
                playerPointsList.add(point);
            }
            points.add(playerPointsList);
        }

        GameInfoDTO gameStateDTO = new GameInfoDTO(points, urls, type, getHandles(game.getPlayers()));
        return gameStateDTO;
    }

    private List<String> getHandles(List<Player> players){
        List<String> handles = new ArrayList<>();
        for(Player player : players){
            handles.add(player.getHandle());
        }
        return handles;
    }
}
