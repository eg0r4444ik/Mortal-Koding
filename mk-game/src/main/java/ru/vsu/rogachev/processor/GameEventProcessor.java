package ru.vsu.rogachev.processor;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.codeforces.CodeforcesClient;
import ru.vsu.rogachev.client.codeforces.dto.CodeforcesUser;
import ru.vsu.rogachev.client.mk.game.dto.async.GameEvent;
import ru.vsu.rogachev.entity.Game;
import ru.vsu.rogachev.entity.GameParameters;
import ru.vsu.rogachev.entity.Player;
import ru.vsu.rogachev.entity.enums.PlayerState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.service.GameService;
import ru.vsu.rogachev.service.PlayerService;

import java.time.LocalDateTime;

import static ru.vsu.rogachev.entity.enums.GameState.NOT_STARTED;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.CODEFORCES_ACCOUNT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GameEventProcessor {

    private final GameService gameService;

    private final PlayerService playerService;

    private final CodeforcesClient codeforcesClient;

    public void process(@NotNull GameEvent gameEvent) {
        switch (gameEvent.getEventType()) {
            case CREATE_GAME -> processCreateGameEvent(gameEvent);
            case JOIN_GAME -> processJoinGameEvent(gameEvent);
            case REFUSE_GAME -> processRefuseGameEvent(gameEvent);
        }
    }

    private void processCreateGameEvent(@NotNull GameEvent gameEvent) {
        Game game = createGame(gameEvent);

        for (String handle : gameEvent.getParticipantsHandles()) {
            createPlayer(handle, gameEvent.getOriginatorRating(), game, PlayerState.NOT_ACTIVE);
        }

        gameService.save(game);
    }

    private void processJoinGameEvent(@NotNull GameEvent gameEvent) {
        playerService.getByHandle(gameEvent.getOriginatorHandle())
                .ifPresentOrElse(p -> p.setState(PlayerState.ACTIVE), () -> {
                    // todo сначала искать свободную игру
                    Game game = createGame(gameEvent);
                    gameService.save(game);
                });
    }

    private void processRefuseGameEvent(@NotNull GameEvent gameEvent) {
        playerService.getByHandle(gameEvent.getOriginatorHandle())
                .ifPresent(p -> p.setState(PlayerState.REFUSED));
    }

    private Game createGame(@NotNull GameEvent gameEvent) {
        GameParameters parameters = getGameParameters(gameEvent);
        Game game = new Game(LocalDateTime.now(), NOT_STARTED, parameters);
        createPlayer(gameEvent.getOriginatorHandle(), gameEvent.getOriginatorRating(), game, PlayerState.ACTIVE);

        return game;
    }

    private GameParameters getGameParameters(@NotNull GameEvent gameEvent) {
        return new GameParameters(
                gameEvent.getParameters().getDuration(),
                gameEvent.getParameters().getType(),
                gameEvent.getParameters().getPlayersCount(),
                gameEvent.getParameters().getTasksCount()
        );
    }

    private void createPlayer(
            @NotNull String handle,
            @NotNull Long rating,
            @NotNull Game game,
            @NotNull PlayerState state
    ) {
        CodeforcesUser codeforcesUser = codeforcesClient.getUserInfo(handle)
                .orElseThrow(() -> BusinessLogicException.of(CODEFORCES_ACCOUNT_NOT_FOUND));
        Player player = new Player(codeforcesUser.getHandle(), rating, state, game);
        game.addPlayer(player);
    }

}
