package ru.vsu.rogachev.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.client.mk.container.ResponseContainer;
import ru.vsu.rogachev.client.mk.game.dto.rest.GetGameStateResponse;
import ru.vsu.rogachev.entity.Game;
import ru.vsu.rogachev.entity.enums.GameState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.exception.BusinessLogicExceptions;
import ru.vsu.rogachev.repository.GameRepository;
import ru.vsu.rogachev.utils.GameStateUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    @NotNull
    public ResponseContainer<GetGameStateResponse> getGameState(@NotNull String handle) {
        Game game = getByPlayerHandle(handle)
                .orElseThrow(() -> BusinessLogicException.of(BusinessLogicExceptions.USER_NOT_IN_GAME));

        return ResponseContainer.success(GameStateUtils.buildGameStateResponse(game));
    }

    public void save(@NotNull Game game){
        gameRepository.save(game);
    }

    @NotNull
    public List<Game> getAll(){
        return gameRepository.findAll();
    }

    @NotNull
    public Optional<Game> getByPlayerHandle(@NotNull String handle) {
        return gameRepository.findByPlayerHandle(handle);
    }

    @NotNull
    public List<Game> getAllByState(@NotNull GameState state) {
        return gameRepository.findAllByState(state);
    }

    @Transactional
    public void delete(@NotNull Game game){
        gameRepository.delete(game);
    }

    @Transactional
    public void deleteAllByState(@NotNull GameState state){
        gameRepository.deleteAllByState(state);
    }

}
