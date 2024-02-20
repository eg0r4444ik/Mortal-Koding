package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;

public interface GameSessionService {

    void add(GameSession gameSession);

    void add(Long time, Long firstUserId, Long secondUserId);

    void add(Long time, User firstUser, User secondUser);

    GameSession getById(Long id);

    GameSession getByFirstUserId(Long id) throws DbDontContainObjectException;

    GameSession getBySecondUserId(Long id) throws DbDontContainObjectException;

    void deleteById(Long id);

    void deleteByFirstUserId(Long id);

    void deleteBySecondUserId(Long id);

}
