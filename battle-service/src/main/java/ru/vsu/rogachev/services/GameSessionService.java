package ru.vsu.rogachev.services;

import ru.vsu.rogachev.Game;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;

import java.util.List;

public interface GameSessionService {

    void add(GameSession gameSession);

    void add(Long time);

    void addPlayer(GameSession game, Player player);

    List<GameSession> getAll();

    GameSession getById(Long id);

    void deleteById(Long id);

}
