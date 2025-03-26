package ru.vsu.rogachev.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entity.ConfirmRequest;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.UserService;
import ru.vsu.rogachev.utils.MessageUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.config.Constants.WAIT_CONFIRMATION_CODE_STATE_BUTTON_CALLBACK_DATA;
import static ru.vsu.rogachev.config.Constants.WAIT_CONFIRMATION_CODE_STATE_BUTTON_TEXTS;
import static ru.vsu.rogachev.entity.enums.UserState.BASIC_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.WAIT_FOR_HANDLE_STATE;

@Service
@RequiredArgsConstructor
public class WaitConfirmationCodeStateCommandHandler implements CommandHandler {

    private static final String SEND_AGAIN_RESULT_TEXT = "На почту был отправлен код подтверждения";
    private static final String CHANGE_HANDLE_RESULT_TEXT = "Введите свой хэндл с сайта Codeforces";
    private static final String SUCCESS_CHECK_CODE_RESULT_TEXT =
            "Вы успешно привязали аккаунт Codeforces с хэндлом '%s' к чату! " +
                    "Теперь вы можете соревноваться с другими участниками, друзьями и отслеживать свой рейтинг!";
    private static final String FAILED_CHECK_CODE_RESULT_TEXT =
            "Вы ввели неверный код, попробуйте снова или запросите повторную отправку кода нажав на кнопку";

    private final MessageUtils messageUtils;

    private final UserService userService;

    private final ConfirmService confirmService;

    @Getter
    @AllArgsConstructor
    enum ProcessedCommand {
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
                confirmService.add(new ConfirmRequest(Objects.requireNonNull(user.getEmail())));
                messageUtils.sendMessage(chatId, SEND_AGAIN_RESULT_TEXT);
            }
            case CHANGE_HANDLE_COMMAND -> {
                userService.setUserState(user, WAIT_FOR_HANDLE_STATE);
                messageUtils.sendMessage(chatId, CHANGE_HANDLE_RESULT_TEXT);
            }
            case CHECK_CODE_COMMAND -> {
                ConfirmRequest confirmRequest = confirmService.getByEmail(Objects.requireNonNull(user.getEmail()))
                        .orElseThrow(() -> new IllegalArgumentException("No confirmation request found"));

                if (Objects.equals(message, confirmRequest.getConfirmationCode())) {
                    userService.activateUser(user);
                    userService.setUserState(user, BASIC_STATE);
                    messageUtils.sendBasicStateMessage(
                            chatId,
                            String.format(SUCCESS_CHECK_CODE_RESULT_TEXT, user.getCodeforcesUsername())
                    );
                    return;
                }

                messageUtils.sendMessageWithButtons(
                        chatId,
                        FAILED_CHECK_CODE_RESULT_TEXT,
                        WAIT_CONFIRMATION_CODE_STATE_BUTTON_TEXTS,
                        WAIT_CONFIRMATION_CODE_STATE_BUTTON_CALLBACK_DATA
                );
            }
        }
    }

}
