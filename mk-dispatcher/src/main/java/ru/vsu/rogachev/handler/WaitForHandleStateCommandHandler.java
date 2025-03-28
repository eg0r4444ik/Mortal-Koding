package ru.vsu.rogachev.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.codeforces.CodeforcesClient;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.mail.MailSenderService;
import ru.vsu.rogachev.services.UserService;
import ru.vsu.rogachev.utils.MessageUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.config.Constants.CODEFORCES_ACCOUNT_EMAIL_IS_BLOCKED;
import static ru.vsu.rogachev.config.Constants.CODEFORCES_ACCOUNT_NOT_FOUNT;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_CONFIRMATION_CODE_STATE;
import static ru.vsu.rogachev.config.Constants.SEND_CONFIRMATION_CODE_TEXT;

@Service
@RequiredArgsConstructor
public class WaitForHandleStateCommandHandler implements CommandHandler {

    private final MessageUtils messageUtils;

    private final CodeforcesClient codeforcesClient;

    private final MailSenderService mailSenderService;

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
                AtomicReference<String> resultMessage = new AtomicReference<>(CODEFORCES_ACCOUNT_NOT_FOUNT);

                try {
                    codeforcesClient.getUserInfo(message).ifPresent(cfUser -> {
                        String email = cfUser.getEmail();
                        if (email != null) {
                            userService.setCodeforcesInfo(user, cfUser);
                            mailSenderService.send(email);
                            userService.setUserState(user, WAIT_CONFIRMATION_CODE_STATE);
                            resultMessage.set(SEND_CONFIRMATION_CODE_TEXT);
                        } else {
                            resultMessage.set(CODEFORCES_ACCOUNT_EMAIL_IS_BLOCKED);
                        }
                    });

                    messageUtils.sendMessage(chatId, resultMessage.get());
                } catch (Exception e) {
                    messageUtils.sendMessage(chatId, resultMessage.get());
                }
            }
        }
    }

}
