package ru.vsu.rogachev.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.services.UserService;
import ru.vsu.rogachev.service.MessageSender;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.entity.enums.UserState.BASIC_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_GAME_CREATION_STATE;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.UNKNOWN_COMMAND;

@Service
@RequiredArgsConstructor
public class WaitGameCreationStateCommandHandler implements CommandHandler {

    private static final String CANCEL_GAME_RESULT_TEXT = "Ожидание подключения игрока завершено!";

    private final MessageSender messageSender;

    private final UserService userService;

    @Getter
    @AllArgsConstructor
    enum ProcessedCommand {
        EXIT_COMMAND("exit", "Прекратить ожидание игры"),
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

        switch (command) {
            case EXIT_COMMAND -> {
                // todo добавить исчезновение сообщения у второго пользователя
                userService.setUserState(user, BASIC_STATE);
                messageSender.sendBasicStateMessage(chatId, CANCEL_GAME_RESULT_TEXT);
            }
            case UNKNOWN -> throw BusinessLogicException.of(chatId, UNKNOWN_COMMAND);
        }
    }

    @Override
    public UserState getHandledState() {
        return WAIT_GAME_CREATION_STATE;
    }

}
