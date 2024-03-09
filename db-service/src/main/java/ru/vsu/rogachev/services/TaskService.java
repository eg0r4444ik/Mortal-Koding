package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.entities.GameSession;

public interface TaskService {

    void add(Task task);

    void add(Long gameId, Long solverId, String taskUrl, Long time);

    void add(GameSession game, User solver, String taskUrl, Long time);

    Task get(Long id);

    void delete(Long id);

}
