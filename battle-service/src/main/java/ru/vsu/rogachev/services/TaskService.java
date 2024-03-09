package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Task;

public interface TaskService {

    void add(Task task);

    void add(Long gameId, String solverHandle, String taskUrl, Long time);

    void add(GameSession game, String solverHandle, String taskUrl, Long time);

    Task get(Long id);

    void delete(Long id);

}
