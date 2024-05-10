package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;

import java.util.Date;

public interface TaskService {

    void add(Task task);

    void add(Long gameId, String taskUrl, int numberInGame);

    void add(GameSession game, String taskUrl, int numberInGame);

    Task get(Long id);

    void setSolver(Task task, Player player, Date date);

    void delete(Long id);

}
