package ru.vsu.rogachev.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.client.MkAuthClient;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.service.UserService;
import ru.vsu.rogachev.service.MessageSender;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.config.Constants.SEND_CONFIRMATION_CODE_TEXT;
import static ru.vsu.rogachev.entity.enums.UserState.BASIC_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_CONFIRMATION_CODE_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_FOR_HANDLE_STATE;
import static ru.vsu.rogachev.service.handler.WaitConfirmationCodeStateCommandHandler.ProcessedCommand.CHANGE_HANDLE_COMMAND;
import static ru.vsu.rogachev.service.handler.WaitConfirmationCodeStateCommandHandler.ProcessedCommand.SEND_AGAIN_COMMAND;

@Service
@RequiredArgsConstructor
public class WaitConfirmationCodeStateCommandHandler implements CommandHandler {

    private static final String CHANGE_HANDLE_RESULT_TEXT = "Введите свой хэндл с сайта Codeforces";
    private static final String SUCCESS_CHECK_CODE_RESULT_TEXT =
            "Вы успешно привязали аккаунт Codeforces с хэндлом '%s' к чату! " +
                    "Теперь вы можете соревноваться с другими участниками, друзьями и отслеживать свой рейтинг!";
    private static final String FAILED_CHECK_CODE_RESULT_TEXT =
            "Вы ввели неверный код, попробуйте снова или запросите повторную отправку кода нажав на кнопку";

    public static final List<String> WAIT_CONFIRMATION_CODE_STATE_BUTTON_TEXTS =
            List.of("Отправить код заново", "Ввести другой хэндл");

    public static final List<String> WAIT_CONFIRMATION_CODE_STATE_BUTTON_CALLBACK_DATA =
            List.of(SEND_AGAIN_COMMAND.getMessage(), CHANGE_HANDLE_COMMAND.getMessage());

    private final MessageSender messageSender;

    private final UserService userService;

    private final MkAuthClient authClient;

    @Getter
    @AllArgsConstructor
    public enum ProcessedCommand {
        SEND_AGAIN_COMMAND("send_again", "Повторить отправку кода подтверждения"),
        CHANGE_HANDLE_COMMAND("change_handle", "Поменять хэндл сайта codeforces"),
        CHECK_CODE_COMMAND("code", "Проверить код подтверждения");

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
                .orElse(ProcessedCommand.CHECK_CODE_COMMAND);

        switch (command) {
            case SEND_AGAIN_COMMAND -> {
                authClient.sendCode(Objects.requireNonNull(user.getEmail()));
                messageSender.sendMessage(chatId, SEND_CONFIRMATION_CODE_TEXT);
            }
            case CHANGE_HANDLE_COMMAND -> {
                userService.setUserState(user, WAIT_FOR_HANDLE_STATE);
                messageSender.sendMessage(chatId, CHANGE_HANDLE_RESULT_TEXT);
            }
            case CHECK_CODE_COMMAND -> {
                boolean isCodeCorrect = authClient.checkCode(Objects.requireNonNull(user.getEmail()), message);

                if (isCodeCorrect) {
                    userService.activateUser(user);
                    userService.setUserState(user, BASIC_STATE);
                    messageSender.sendBasicStateMessage(
                            chatId,
                            String.format(SUCCESS_CHECK_CODE_RESULT_TEXT, user.getCodeforcesUsername())
                    );
                    return;
                }

                messageSender.sendMessageWithButtons(
                        chatId,
                        FAILED_CHECK_CODE_RESULT_TEXT,
                        WAIT_CONFIRMATION_CODE_STATE_BUTTON_TEXTS,
                        WAIT_CONFIRMATION_CODE_STATE_BUTTON_CALLBACK_DATA
                );
            }
        }
    }

    @Override
    public UserState getHandledState() {
        return WAIT_CONFIRMATION_CODE_STATE;
    }

}
