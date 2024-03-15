package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.Game;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.enums.PlayerState;
import ru.vsu.rogachev.repositories.GameSessionRepository;
import ru.vsu.rogachev.services.GameSessionService;

import java.util.List;

@Service
public class GameSessionServiceImpl implements GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    public void add(GameSession gameSession){
        gameSessionRepository.save(gameSession);
    }

    public void add(Long time, Long playersCount) {
        GameSession gameSession = new GameSession(time, playersCount);
        gameSessionRepository.save(gameSession);
    }

    public void addPlayer(GameSession game, Player player){
        game.addPlayer(player);
        gameSessionRepository.save(game);
    }

    public List<GameSession> getAll() {
        return gameSessionRepository.findAll();
    }
    public GameSession getById(Long id){
        return gameSessionRepository.getById(id);
    }

    @Transactional
    public void deleteById(Long id){
        gameSessionRepository.deleteById(id);
    }
}
