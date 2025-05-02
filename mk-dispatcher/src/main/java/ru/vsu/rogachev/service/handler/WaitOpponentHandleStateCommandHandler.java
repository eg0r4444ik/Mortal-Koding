package ru.vsu.rogachev.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.codeforces.CodeforcesClient;
import ru.vsu.rogachev.client.codeforces.dto.CodeforcesUser;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.services.InviteService;
import ru.vsu.rogachev.services.UserService;
import ru.vsu.rogachev.service.MessageSender;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.entity.enums.UserState.BASIC_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_GAME_CREATION_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_OPPONENT_HANDLE_STATE;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.CODEFORCES_ACCOUNT_NOT_FOUND;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.USER_ALREADY_IN_GAME;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.USER_CREATING_THE_GAME;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.USER_NOT_CONFIRM_ACCOUNT;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.USER_NOT_REGISTERED;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.USER_WAITING_CONNECTION_TO_ANOTHER_GAME;
import static ru.vsu.rogachev.service.handler.BasicStateCommandHandler.ProcessedCommand.AGREE_GAME_COMMAND;
import static ru.vsu.rogachev.service.handler.BasicStateCommandHandler.ProcessedCommand.REFUSE_GAME_COMMAND;

@Service
@RequiredArgsConstructor
public class WaitOpponentHandleStateCommandHandler implements CommandHandler {

    private static final String CANCEL_CHOOSING_OPPONENT_RESULT_TEXT = "Приглашение игрока в игру отменено";
    private static final String SENT_INVITE_RESULT_TEXT =
            "Приглашение в игру отправлено, ожидаем подключения...";
    public static final String GAME_INVITE_TEXT = "Пользователь %s с ником на Codeforces %s вызвал вас на дуэль!";

    public static final List<String> GAME_INVITE_BUTTON_TEXTS =
            List.of("Согласиться", "Отказаться");

    public static final List<String> GAME_INVITE_BUTTON_CALLBACK_DATA =
            List.of(AGREE_GAME_COMMAND.getMessage(), REFUSE_GAME_COMMAND.getMessage());

    private final MessageSender messageSender;

    private final CodeforcesClient codeforcesClient;
    
    private final UserService userService;

    private final InviteService inviteService;

    @Getter
    @AllArgsConstructor
    enum ProcessedCommand {
        EXIT_COMMAND("exit", "Отмена приглашения в игру"),
        SELECT_HANDLE_COMMAND("select_handle_command", "Выбор хэндла");

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
                .orElse(ProcessedCommand.SELECT_HANDLE_COMMAND);

        switch (command) {
            case EXIT_COMMAND -> {
                userService.setUserState(user, BASIC_STATE);
                messageSender.sendBasicStateMessage(chatId, CANCEL_CHOOSING_OPPONENT_RESULT_TEXT);
            }
            case SELECT_HANDLE_COMMAND -> {
                // todo добавить кнопку отмены при неверном хэндле
                try {
                    CodeforcesUser cfUser = codeforcesClient.getUserInfo(message)
                            .orElseThrow(() -> BusinessLogicException.of(chatId, CODEFORCES_ACCOUNT_NOT_FOUND, message));
                    Optional<User> opponentOptional = userService.getUserByCodeforcesUsername(cfUser.getHandle());

                    if (opponentOptional.isEmpty()) {
                        throw BusinessLogicException.of(chatId, USER_NOT_REGISTERED, message);
                    }

                    User opponent = opponentOptional.get();
                    validateOpponent(opponent, chatId);

//                    gameEventService.createGame(user, new GameParameters(DEFAULT, 2L ,5L));

                    messageSender.sendMessageWithButtons(
                            opponent.getChatId(),
                            String.format(GAME_INVITE_TEXT, user.getUsername(), user.getCodeforcesUsername()),
                            GAME_INVITE_BUTTON_TEXTS,
                            GAME_INVITE_BUTTON_CALLBACK_DATA
                    );
                    inviteService.add(opponent, user);

                    userService.setUserState(user, WAIT_GAME_CREATION_STATE);
                    messageSender.sendMessage(chatId, SENT_INVITE_RESULT_TEXT);
                } catch (Exception e) {
                    throw BusinessLogicException.of(chatId, CODEFORCES_ACCOUNT_NOT_FOUND, message);
                }
            }
        }
    }

    @Override
    public UserState getHandledState() {
        return WAIT_OPPONENT_HANDLE_STATE;
    }

    private void validateOpponent(@NotNull User opponent, @NotNull Long chatId) {
        switch (opponent.getState()) {
            case WAIT_CONFIRMATION_CODE_STATE -> {
                throw BusinessLogicException.of(chatId, USER_NOT_CONFIRM_ACCOUNT, opponent.getCodeforcesUsername());
            }
            case WAIT_OPPONENT_HANDLE_STATE -> {
                throw BusinessLogicException.of(chatId, USER_CREATING_THE_GAME);
            }
            case WAIT_GAME_CREATION_STATE -> {
                throw BusinessLogicException.of(chatId, USER_WAITING_CONNECTION_TO_ANOTHER_GAME);
            }
            case DURING_THE_GAME -> {
                throw BusinessLogicException.of(chatId, USER_ALREADY_IN_GAME);
            }
        }
    }

}
