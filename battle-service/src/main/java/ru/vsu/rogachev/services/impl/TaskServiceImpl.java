package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.repositories.TaskRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GameSessionService gameSessionService;

    public void add(Task task){
        taskRepository.save(task);
    }

    public void add(Long gameId, String solverHandle, String taskUrl, Long time){
        Task task = new Task(gameSessionService.getById(gameId), solverHandle, taskUrl, time);
        taskRepository.save(task);
    }

    public void add(GameSession game, String solverHandle, String taskUrl, Long time){
        Task task = new Task(game, solverHandle, taskUrl, time);
        taskRepository.save(task);
    }

    public Task get(Long id){
        return taskRepository.getById(id);
    }

    @Transactional
    public void delete(Long id){
        taskRepository.deleteById(id);
    }

}
