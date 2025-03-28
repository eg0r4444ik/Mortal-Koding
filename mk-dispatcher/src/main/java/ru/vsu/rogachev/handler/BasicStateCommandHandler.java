package ru.vsu.rogachev.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.services.UserService;
import ru.vsu.rogachev.utils.MessageUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.config.Constants.NON_ACTIVE_ACCOUNT_MESSAGE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_FOR_HANDLE_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_OPPONENT_HANDLE_STATE;

@Service
@RequiredArgsConstructor
public class BasicStateCommandHandler implements CommandHandler {

    private static final String GREETING_MESSAGE =
            "Привет! Это бот для дуэлей по спортивному программированию. Привяжите свой аккаунт на codeforces к " +
                    "этому боту, соревнуйтесь с друзьями и другими пользователями и повышайте свой рейтинг!";

    private static final String SET_OPPONENT_HANDLE_MESSAGE = "Введите хэндл оппонента с сайта codeforces";

    private static final String GET_RATING_MESSAGE = "Ваш рейтинг: %s";

    private final MessageUtils messageUtils;

    private final UserService userService;

    @Getter
    @AllArgsConstructor
    enum ProcessedCommand {
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
            messageUtils.sendMessage(chatId, NON_ACTIVE_ACCOUNT_MESSAGE);
        }

        switch (command) {
            case START_COMMAND -> {
                // todo добавить проверку что пользователь уже активирал учетную запись
                userService.setUserState(user, WAIT_FOR_HANDLE_STATE);
                messageUtils.sendMessage(chatId, GREETING_MESSAGE);
            }
            case FIND_GAME_COMMAND -> {
                messageUtils.sendNotSupportedYetCommandMessage(chatId);
            }
            case PLAY_WITH_FRIEND_COMMAND -> {
                userService.setUserState(user, WAIT_OPPONENT_HANDLE_STATE);
                messageUtils.sendMessage(chatId, SET_OPPONENT_HANDLE_MESSAGE);
            }
            case LOOK_RATING_COMMAND -> {
                messageUtils.sendBasicStateMessage(chatId, String.format(GET_RATING_MESSAGE, user.getRating()));
            }
            case LOOK_GAMES_HISTORY_COMMAND -> {

            }
            case AGREE_GAME_COMMAND -> {

            }
            case REFUSE_GAME_COMMAND -> {

            }
            case UNKNOWN -> messageUtils.sendUnknownCommandMessage(chatId);
        }
    }

}
