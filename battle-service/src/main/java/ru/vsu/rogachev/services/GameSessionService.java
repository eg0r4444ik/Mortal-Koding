package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.GameSession;

public interface GameSessionService {

    void add(GameSession gameSession);

    void add(Long time);

    GameSession getById(Long id);

    void deleteById(Long id);

}
