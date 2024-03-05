package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.repositories.GameSessionRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.UserService;

@Service
public class GameSessionServiceImpl implements GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;
    @Autowired
    private UserService userService;

    public void add(GameSession gameSession){
        gameSessionRepository.save(gameSession);
    }

    public void add(Long time, Long playersCount) {
        GameSession gameSession = new GameSession(time, playersCount);
        gameSessionRepository.save(gameSession);
    }

    public GameSession getById(Long id){
        return gameSessionRepository.getById(id);
    }

    @Transactional
    public void deleteById(Long id){
        gameSessionRepository.deleteById(id);
    }
}
