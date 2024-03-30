package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.repositories.GameSessionRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.PlayerService;

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

    @Transactional
    @Override
    public void deleteById(Long id){
        gameSessionRepository.deleteById(id);
    }
}
