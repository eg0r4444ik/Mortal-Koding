package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.repositories.TaskRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.TaskService;
import ru.vsu.rogachev.services.UserService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GameSessionService gameSessionService;
    @Autowired
    private UserService userService;

    public void add(Task task){
        taskRepository.save(task);
    }

    public void add(Long gameId, Long solverId, String taskUrl, Long time){
        Task task = new Task(gameSessionService.getById(gameId), userService.getUserById(solverId), taskUrl, time);
        taskRepository.save(task);
    }

    public void add(GameSession game, User solver, String taskUrl, Long time){
        Task task = new Task(game, solver, taskUrl, time);
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
