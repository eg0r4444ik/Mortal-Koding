package ru.vsu.rogachev.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.services.GameService;
import ru.vsu.rogachev.service.MessageSender;

import java.time.Duration;
import java.time.LocalDateTime;
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

    private final MessageSender messageSender;

    private final GameService gameService;

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
                gameService.getByPlayerHandle(Objects.requireNonNull(user.getCodeforcesUsername()))
                        .ifPresent(game -> {
                            Duration time = Duration.between(LocalDateTime.now(), game.getStartTime());
                            messageSender.sendMessage(
                                    chatId,
                                    String.format(TIMER_RESULT_MESSAGE, time.toMinutes(), time.getSeconds() % 60)
                            );
                        });
            }
            case UNKNOWN -> throw BusinessLogicException.of(chatId, UNKNOWN_COMMAND);
        }
    }

    @Override
    public UserState getHandledState() {
        return DURING_THE_GAME;
    }

}
