package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;

public interface GameSessionService {

    void add(GameSession gameSession);

    void add(Long time, Long playersCount);

    GameSession getById(Long id);

    void deleteById(Long id);

}
