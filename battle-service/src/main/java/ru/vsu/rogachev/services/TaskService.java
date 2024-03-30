package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;

public interface TaskService {

    void add(Task task);

    void add(Long gameId, String taskUrl);

    void add(GameSession game, String taskUrl);

    Task get(Long id);

    void delete(Long id);

}
