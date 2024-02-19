package ru.vsu.rogachev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.repositories.GameSessionRepository;

@Service
public class GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    public void add(GameSession gameSession){
        gameSessionRepository.save(gameSession);
    }

    public void add(Long time, Long firstUserId, Long secondUserId){
        GameSession gameSession = new GameSession(time, firstUserId, secondUserId);
        gameSessionRepository.save(gameSession);
    }

    public GameSession getById(Long id){
        return gameSessionRepository.getById(id);
    }

    public GameSession getByFirstUserId(Long id){
        return gameSessionRepository.findByFirstUserId(id).get();
    }

    public GameSession getBySecondUserId(Long id){
        return gameSessionRepository.findBySecondUserId(id).get();
    }

    public void deleteById(Long id){
        gameSessionRepository.deleteById(id);
    }

    public void deleteByFirstUserId(Long id){
        gameSessionRepository.deleteByFirstUserId(id);
    }

    public void deleteBySecondUserId(Long id){
        gameSessionRepository.deleteBySecondUserId(id);
    }

}
