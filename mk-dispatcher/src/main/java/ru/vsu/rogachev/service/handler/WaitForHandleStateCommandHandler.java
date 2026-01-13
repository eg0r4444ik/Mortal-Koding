package ru.vsu.rogachev.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.MkAuthClient;
import ru.vsu.rogachev.client.codeforces.CodeforcesClient;
import ru.vsu.rogachev.client.codeforces.dto.CodeforcesUser;
import ru.vsu.rogachev.entity.dao.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.service.UserService;
import ru.vsu.rogachev.service.message.MessageSender;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.entity.enums.UserState.WAIT_CONFIRMATION_CODE_STATE;
import static ru.vsu.rogachev.config.Constants.SEND_CONFIRMATION_CODE_TEXT;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_FOR_HANDLE_STATE;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.CODEFORCES_ACCOUNT_EMAIL_IS_BLOCKED;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.CODEFORCES_ACCOUNT_NOT_FOUND;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.COMMON_LOGIC_EXCEPTION;

@Service
@RequiredArgsConstructor
public class WaitForHandleStateCommandHandler implements CommandHandler {

    private final MessageSender messageSender;

    private final CodeforcesClient codeforcesClient;

    private final MkAuthClient authClient;

    private final UserService userService;

    @Getter
    @AllArgsConstructor
    enum ProcessedCommand {
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
            case SELECT_HANDLE_COMMAND -> {
                try {
                    CodeforcesUser cfUser = codeforcesClient.getUserInfo(message)
                            .orElseThrow(() -> BusinessLogicException.of(chatId, CODEFORCES_ACCOUNT_NOT_FOUND, message));
                    String email = cfUser.getEmail();

                    if (email == null) {
                        throw BusinessLogicException.of(chatId, CODEFORCES_ACCOUNT_EMAIL_IS_BLOCKED, message);
                    }

                    userService.setCodeforcesInfo(user, cfUser);
                    authClient.sendCode(email);
                    userService.setUserState(user, WAIT_CONFIRMATION_CODE_STATE);
                    messageSender.sendMessage(chatId, SEND_CONFIRMATION_CODE_TEXT);
                } catch (Exception e) {
                    throw BusinessLogicException.of(chatId, COMMON_LOGIC_EXCEPTION, message);
                }
            }
        }
    }

    @Override
    public UserState getHandledState() {
        return WAIT_FOR_HANDLE_STATE;
    }

}
