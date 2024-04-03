package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.connectingPlayers.GameDistributor;
import ru.vsu.rogachev.dto.GameDTO;

import java.util.List;

@RequestMapping("/game")
@RestController
public class GameController {

    @Autowired
    GameDistributor gameDistributor;

    @PostMapping("/connect")
    public ResponseEntity<?> connect(@RequestBody GameDTO gameDTO) throws JsonProcessingException, InterruptedException {
        gameDistributor.connectPlayerToGame(gameDTO.getHandle(), gameDTO.getPlayersCount(), gameDTO.getTime(), gameDTO.getTasksCount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start")
    public ResponseEntity<?> start(@RequestBody GameDTO gameDTO) throws InterruptedException, JsonProcessingException {
        gameDistributor.createGameWithPlayers(gameDTO.getHandle(), gameDTO.getPlayersCount(), gameDTO.getTime(),
                gameDTO.getTasksCount(), gameDTO.getHandles());
        return ResponseEntity.ok().build();
    }
}