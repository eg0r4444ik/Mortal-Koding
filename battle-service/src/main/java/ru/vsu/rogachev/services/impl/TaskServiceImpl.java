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
    public void add(Long gameId, String solverHandle, String taskUrl, Long time){
        Task task = new Task(gameSessionService.getById(gameId), playerService.getByHandle(solverHandle), taskUrl, time);
        taskRepository.save(task);
    }

    @Override
    public void add(GameSession game, Player solver, String taskUrl, Long time){
        Task task = new Task(game, solver, taskUrl, time);
        taskRepository.save(task);
    }

    @Override
    public Task get(Long id){
        return taskRepository.getById(id);
    }

    @Transactional
    @Override
    public void delete(Long id){
        taskRepository.deleteById(id);
    }

}
