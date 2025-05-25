package ru.vsu.rogachev.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.MkGameClient;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.service.UserService;
import ru.vsu.rogachev.service.MessageSender;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.entity.enums.UserState.BASIC_STATE;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.NON_ACTIVE_ACCOUNT;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_FOR_HANDLE_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_OPPONENT_HANDLE_STATE;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.OPERATION_NOT_SUPPORTED_YET;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.UNKNOWN_COMMAND;

@Service
@RequiredArgsConstructor
public class BasicStateCommandHandler implements CommandHandler {

    private static final String GREETING_MESSAGE =
            "Привет! Это бот для дуэлей по спортивному программированию. Привяжите свой аккаунт на codeforces к " +
                    "этому боту, соревнуйтесь с друзьями и другими пользователями и повышайте свой рейтинг!";

    private static final String SET_OPPONENT_HANDLE_MESSAGE = "Введите хэндл оппонента с сайта codeforces";

    private static final String GET_RATING_MESSAGE = "Ваш рейтинг: %s";

    private final MessageSender messageSender;

    private final UserService userService;

    private final MkGameClient gameClient;

//    private final KafkaTemplate<String, GameEvent> gameEventKafkaTemplate;

    @Getter
    @AllArgsConstructor
    public enum ProcessedCommand {
        START_COMMAND("/start", "Начало взаимодействия с ботом"),
        FIND_GAME_COMMAND("find_game", "Поиск игры"),
        PLAY_WITH_FRIEND_COMMAND("play_with_friend", "Создание игры с другом"),
        LOOK_RATING_COMMAND("look_rating", "Просмотр своего рейтинга"),
        LOOK_GAMES_HISTORY_COMMAND("look_games_history", "Посмотреть историю игр"),
        AGREE_GAME_COMMAND("agree_game", "Согласиться на приглашение к игре"),
        REFUSE_GAME_COMMAND("refuse_game", "Отклонить приглашение к игре"),
        UNKNOWN("unknown", "Неизвестная команда");

        private final String message;

        private final String description;

        private static final Map<String, ProcessedCommand> LOOKUP = Arrays.stream(values())
                .collect(Collectors.toMap(ProcessedCommand::getMessage, Function.identity()));

        public static @NotNull Optional<ProcessedCommand> getCommandByMessage(@NotNull String message) {
            return Optional.ofNullable(LOOKUP.get(message));
        }
    }

    @Override
    public void execute(@NotNull User user, @NotNull Long chatId, @NotNull String message) {
        ProcessedCommand command = ProcessedCommand.getCommandByMessage(message)
                .orElse(ProcessedCommand.UNKNOWN);

        if (!user.getIsActive() && command != ProcessedCommand.START_COMMAND) {
            userService.setUserState(user, WAIT_FOR_HANDLE_STATE);
            throw BusinessLogicException.of(chatId, NON_ACTIVE_ACCOUNT);
        }

        switch (command) {
            case START_COMMAND -> {

//                GameEvent gameEvent = new GameEvent(LocalDate.now(), GameEventType.CREATE_GAME, "egor444ik",
//                        new GameEvent.GameParameters(Duration.ofMinutes(15), GameType.DEFAULT, 2L, 5L),
//                        List.of("iamdimonis", "destroyer"));
//                gameEventKafkaTemplate.send("game-event-topic", gameEvent);

                if (!user.getIsActive()) {
                    userService.setUserState(user, WAIT_FOR_HANDLE_STATE);
                }
                messageSender.sendMessage(chatId, GREETING_MESSAGE);
            }
            case FIND_GAME_COMMAND -> {
                throw BusinessLogicException.of(chatId, OPERATION_NOT_SUPPORTED_YET);
            }
            case PLAY_WITH_FRIEND_COMMAND -> {
                userService.setUserState(user, WAIT_OPPONENT_HANDLE_STATE);
                messageSender.sendMessage(chatId, SET_OPPONENT_HANDLE_MESSAGE);
            }
            case LOOK_RATING_COMMAND -> {
                messageSender.sendBasicStateMessage(chatId, String.format(GET_RATING_MESSAGE, user.getRating()));
            }
            case LOOK_GAMES_HISTORY_COMMAND -> {
                throw BusinessLogicException.of(chatId, OPERATION_NOT_SUPPORTED_YET);
            }
            case AGREE_GAME_COMMAND -> {
//                gameClient.connectToGame(user);
            }
            case REFUSE_GAME_COMMAND -> {
//                gameClient.refuseGame(user);
            }
            case UNKNOWN -> throw BusinessLogicException.of(chatId, UNKNOWN_COMMAND);
        }
    }

    @Override
    public UserState getHandledState() {
        return BASIC_STATE;
    }

}
