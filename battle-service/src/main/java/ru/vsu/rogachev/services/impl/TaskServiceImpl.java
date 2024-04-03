package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.repositories.TaskRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.PlayerService;
import ru.vsu.rogachev.services.TaskService;

import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GameSessionService gameSessionService;
    @Autowired
    private PlayerService playerService;

    @Override
    public void add(Task task){
        taskRepository.save(task);
    }

    @Override
    public void add(Long gameId, String taskUrl){
        Task task = new Task(gameSessionService.getById(gameId), taskUrl);
        taskRepository.save(task);
    }

    @Override
    public void add(GameSession game, String taskUrl){
        Task task = new Task(game, taskUrl);
        taskRepository.save(task);
    }

    @Override
    public Task get(Long id){
        return taskRepository.getById(id);
    }

    @Override
    public void setSolver(Task task, Player player) {
        task.setSolver(player);
        task.setTime(new Date());
        taskRepository.save(task);
    }

    @Transactional
    @Override
    public void delete(Long id){
        taskRepository.deleteById(id);
    }

}
