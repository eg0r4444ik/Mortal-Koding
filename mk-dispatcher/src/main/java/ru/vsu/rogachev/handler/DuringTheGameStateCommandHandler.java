package ru.vsu.rogachev.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.utils.MessageUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DuringTheGameStateCommandHandler implements CommandHandler {

    private final MessageUtils messageUtils;

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

            }
            case UNKNOWN -> messageUtils.sendUnknownCommandMessage(chatId);
        }
    }

}
