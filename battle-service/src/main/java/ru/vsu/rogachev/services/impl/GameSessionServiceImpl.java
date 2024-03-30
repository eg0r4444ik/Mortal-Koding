package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
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
    public void add(Long time, Long playersCount) {
        GameSession gameSession = new GameSession(time, playersCount);
        gameSessionRepository.save(gameSession);
    }

    @Override
    public void addPlayer(GameSession game, Player player){
        game.addPlayer(player);
        playerService.setGameSession(player.getHandle(), game.getId());
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
