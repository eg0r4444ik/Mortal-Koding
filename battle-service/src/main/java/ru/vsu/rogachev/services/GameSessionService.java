package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;

import java.util.List;

public interface GameSessionService {

    void add(GameSession gameSession);

    void add(Long time, Long playersCount, Long tasksCount);

    void addActivePlayer(GameSession game, Player player);

    void addNotActivePlayer(GameSession game, Player player);

    void addTasks(GameSession game, List<Task> tasks);

    List<GameSession> getAll();

    GameSession getById(Long id);

    void startGame(Long id);

    void stopGame(Long id);

    void deleteById(Long id);

}
