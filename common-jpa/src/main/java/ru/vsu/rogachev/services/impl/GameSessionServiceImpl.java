package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void add(Long time, Long firstUserId, Long secondUserId){
        GameSession gameSession = new GameSession(time, userService.getUserById(firstUserId), userService.getUserById(secondUserId));
        gameSessionRepository.save(gameSession);
    }

    public void add(Long time, User firstUser, User secondUser){
        GameSession gameSession = new GameSession(time, firstUser, secondUser);
        gameSessionRepository.save(gameSession);
    }

    public GameSession getById(Long id){
        return gameSessionRepository.getById(id);
    }

    public GameSession getByFirstUserId(Long id) throws DbDontContainObjectException {
        if(gameSessionRepository.findByFirstUserId(id).isEmpty()){
            throw new DbDontContainObjectException();
        }
        return gameSessionRepository.findByFirstUserId(id).get();
    }

    public GameSession getBySecondUserId(Long id) throws DbDontContainObjectException {
        if(gameSessionRepository.findBySecondUserId(id).isEmpty()){
            throw new DbDontContainObjectException();
        }
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
