package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.services.TaskService;
import ru.vsu.rogachev.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/add")
    public void add(@RequestBody String taskJSON, Model model) throws JsonProcessingException {
        Task task = objectMapper.readValue(taskJSON, Task.class);
        taskService.add(task);
    }

    @PostMapping("/addByParams")
    public void add(@RequestBody Long gameId, @RequestBody Long solverId,
                    @RequestBody String taskUrl,  @RequestBody Long time){
        taskService.add(gameId, solverId, taskUrl, time);
    }

    @PostMapping("/addByParamsObj")
    public void add(@RequestBody GameSession game, @RequestBody User solver,
                    @RequestBody String taskUrl, @RequestBody Long time){
        taskService.add(game, solver, taskUrl, time);
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable(value = "id") long id) throws JsonProcessingException {
        Task task = taskService.get(id);
        String response = objectMapper.writeValueAsString(task);
        return response;
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable(value = "id") long id){
        taskService.delete(id);
    }
}
