package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.distributor.GameDistributor;
import ru.vsu.rogachev.dto.GameDTO;
import ru.vsu.rogachev.services.GameSessionService;

@RequestMapping("/game")
@RestController
public class GameController {

    @Autowired
    private GameDistributor gameDistributor;

    @Autowired
    private GameSessionService gameSessionService;

//    @PostMapping("/connect")
//    public ResponseEntity<?> connect(@RequestBody GameDTO gameDTO) throws JsonProcessingException, InterruptedException {
//        gameDistributor.connectPlayerToGame(gameDTO.getHandle(), gameDTO.getPlayersCount(), gameDTO.getTime(), gameDTO.getTasksCount());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/start")
//    public ResponseEntity<?> start(@RequestBody GameDTO gameDTO) throws InterruptedException, JsonProcessingException {
//        gameDistributor.createGameWithPlayers(gameDTO.getHandle(), gameDTO.getPlayersCount(), gameDTO.getTime(),
//                gameDTO.getTasksCount(), gameDTO.getHandles());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/connect/{handle}/{id}")
//    public ResponseEntity<?> connectById(@PathVariable(value = "handle") String handle, @PathVariable(value = "id") long gameId) throws InterruptedException, JsonProcessingException {
//        gameDistributor.connectPlayerToGame(handle, gameId);
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/getTime/{handle}")
//    public long getTime(@PathVariable(value = "handle") String handle){
//
//    }
}