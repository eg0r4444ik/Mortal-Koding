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
public class WaitOpponentHandleStateCommandHandler implements CommandHandler {

    private final MessageUtils messageUtils;

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

            }
            case SELECT_HANDLE_COMMAND -> {

            }
        }
    }

}
