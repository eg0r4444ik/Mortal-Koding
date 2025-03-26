package ru.vsu.rogachev.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entity.Task;
import ru.vsu.rogachev.repositories.TaskRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public void add(@NotNull Task task){
        taskRepository.save(task);
    }

    public @NotNull Optional<Task> get(@NotNull Long id){
        return taskRepository.findById(id);
    }

    @Transactional
    public void delete(@NotNull Long id){
        taskRepository.deleteById(id);
    }

}
