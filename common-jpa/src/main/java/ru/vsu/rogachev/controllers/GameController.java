package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameSessionService gameSessionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/add")
    public void add(@RequestBody String gameJSON) throws JsonProcessingException {
        GameSession game = objectMapper.readValue(gameJSON, GameSession.class);
        gameSessionService.add(game);
    }

    @PostMapping("/addByParams")
    public void add(@RequestBody Long time, @RequestBody Long playersCount) {
        gameSessionService.add(time, playersCount);
    }

    @GetMapping("/get")
    public String getById(@RequestBody Long id) throws JsonProcessingException {
        GameSession game = gameSessionService.getById(id);
        String response = objectMapper.writeValueAsString(game);
        return response;
    }

    @GetMapping("/delete")
    public void deleteById(@RequestBody Long id){
        gameSessionService.deleteById(id);
    }
}
