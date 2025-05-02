package ru.vsu.rogachev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.client.mk.container.ResponseContainer;
import ru.vsu.rogachev.client.mk.game.dto.rest.GetGameStateResponse;
import ru.vsu.rogachev.service.GameService;

import static ru.vsu.rogachev.client.mk.game.GameEndpoints.GET_GAME_STATE_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @PostMapping(GET_GAME_STATE_ENDPOINT)
    public ResponseContainer<GetGameStateResponse> getGameState(@RequestBody String handle){
        return gameService.getGameState(handle);
    }

}
