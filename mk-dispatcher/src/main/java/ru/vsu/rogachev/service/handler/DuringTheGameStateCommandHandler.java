package ru.vsu.rogachev.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.MkGameClient;
import ru.vsu.rogachev.entity.dao.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.service.message.MessageSender;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.entity.enums.UserState.DURING_THE_GAME;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.UNKNOWN_COMMAND;

@Service
@RequiredArgsConstructor
public class DuringTheGameStateCommandHandler implements CommandHandler {

    private static final String TIMER_RESULT_MESSAGE = "С начала соревнования прошло: %s минут, %s секунд";

    private final MkGameClient gameClient;

    private final MessageSender messageSender;

    @Getter
    @AllArgsConstructor
    enum ProcessedCommand {
        SHOW_TIMER_COMMAND("show_timer", "Посмотреть оставшееся игровое время"),
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
            case SHOW_TIMER_COMMAND -> {
                Duration timeUntilEnd = gameClient.getGameState(Objects.requireNonNull(user.getCodeforcesUsername()))
                        .getState()
                        .getTimeUntilEnd();
                messageSender.sendMessage(chatId, String.format(TIMER_RESULT_MESSAGE, timeUntilEnd.toMinutes(), timeUntilEnd.getSeconds() % 60));
            }
            case UNKNOWN -> throw BusinessLogicException.of(chatId, UNKNOWN_COMMAND);
        }
    }

    @Override
    public UserState getHandledState() {
        return DURING_THE_GAME;
    }

}
