package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.connectingPlayers.GameDistributor;

import java.util.List;

@RequestMapping("/game")
@RestController
public class GameController {

    @Autowired
    GameDistributor gameDistributor;

    @PostMapping("/connect")
    public ResponseEntity<?> connect(@RequestBody String handle, @RequestBody long playersCount,
                                     @RequestBody long time, @RequestBody long tasksCount) throws JsonProcessingException, InterruptedException {
        gameDistributor.connectPlayerToGame(handle, playersCount, time, tasksCount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start")
    public ResponseEntity<?> start(@RequestBody String handle, @RequestBody long playersCount,
                                   @RequestBody long time, @RequestBody long tasksCount, @RequestBody List<String> handles) throws InterruptedException, JsonProcessingException {
        gameDistributor.createGameWithPlayers(handle, playersCount, time, tasksCount, handles);
        return ResponseEntity.ok().build();
    }
}