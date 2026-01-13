package ru.vsu.rogachev.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.mk.game.dto.CurrentGameState;
import ru.vsu.rogachev.client.mk.game.dto.async.GameStateUpdateEvent;
import ru.vsu.rogachev.entity.dao.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.service.message.BasicStateMessageSender;
import ru.vsu.rogachev.service.message.MessageSender;
import ru.vsu.rogachev.service.UserService;
import ru.vsu.rogachev.utils.GameMessageUtils;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static ru.vsu.rogachev.exception.BusinessLogicExceptions.RATING_RECALCULATION_ERROR;

@Service
@RequiredArgsConstructor
public class GameStateUpdateProcessor {

    private final UserService userService;

    private final MessageSender messageSender;

    private final BasicStateMessageSender basicStateMessageSender;

    public void process(@NotNull GameStateUpdateEvent event) {
        event.getState().getPlayersScores()
                .stream()
                .map(CurrentGameState.PlayersScore::getPlayerHandle)
                .distinct()
                .map(handle -> userService.getUserByCodeforcesUsername(handle).orElse(null))
                .filter(Objects::nonNull)
                .forEach(user -> processEventForUser(user, event));
    }

    private void processEventForUser(@NotNull User user, @NotNull GameStateUpdateEvent event) {
        switch (event.getEventType()) {
            case START_GAME -> {
                userService.setUserState(user, UserState.DURING_THE_GAME);
                messageSender.sendMessage(GameMessageUtils.getGameTasks(user.getChatId(), event.getState().getTasks()));
            }
            case SOLVE_TASKS -> {
                messageSender.sendMessage(GameMessageUtils.getGameStateMessage(user.getChatId(), event.getState()));
            }
            case END_GAME -> {
                GameStateUpdateEvent.PlayersRating rating = event.getRecalculatedRatings()
                        .stream()
                        .filter(r -> r.getPlayerHandle().equals(user.getCodeforcesUsername()))
                        .findFirst()
                        .orElseThrow(() -> BusinessLogicException.of(user.getChatId(), RATING_RECALCULATION_ERROR));

                userService.setUserState(user, UserState.BASIC_STATE);
                userService.setUserRating(user, rating.getNewRating());

                messageSender.sendMessage(
                        GameMessageUtils.getEndGameMessage(
                                user.getChatId(),
                                event.getState()
                        )
                );
                basicStateMessageSender.sendMessage(
                        GameMessageUtils.getRatingChangesMessage(
                                user.getChatId(),
                                rating.getOldRating(),
                                rating.getNewRating()
                        )
                );
            }
        }
    }

}
