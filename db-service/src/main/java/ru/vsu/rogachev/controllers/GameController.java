package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.dto.GameDTO;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.services.GameSessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameSessionService gameSessionService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping("/add")
    public void add(@RequestBody GameDTO gameDTO) {
        gameSessionService.add(gameDTO.getTime(), gameDTO.getPlayersCount());
    }

    @GetMapping("/get")
    public String getById(@RequestBody Long id) throws JsonProcessingException {
        GameSession game = gameSessionService.getById(id);
        GameDTO dto = new GameDTO(game);
        String response = objectMapper.writeValueAsString(dto);
        return response;
    }

    @GetMapping("/delete")
    public void deleteById(@RequestBody Long id){
        gameSessionService.deleteById(id);
    }
}
