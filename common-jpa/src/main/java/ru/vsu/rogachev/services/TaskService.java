package ru.vsu.rogachev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public void add(Task task){
        taskRepository.save(task);
    }

    public void add(Long gameId, Long solverId, String taskUrl, Long time){
        Task task = new Task(gameId, solverId, taskUrl, time);
        taskRepository.save(task);
    }

    public Task get(Long id){
        return taskRepository.getById(id);
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }

}