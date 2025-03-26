package ru.vsu.rogachev.distributor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vsu.rogachev.handler.WaitGameCreationStateCommandHandler;
import ru.vsu.rogachev.handler.WaitConfirmationCodeStateCommandHandler;
import ru.vsu.rogachev.handler.CommandHandler;
import ru.vsu.rogachev.handler.BasicStateCommandHandler;
import ru.vsu.rogachev.handler.DuringTheGameStateCommandHandler;
import ru.vsu.rogachev.handler.WaitForHandleStateCommandHandler;
import ru.vsu.rogachev.handler.WaitOpponentHandleStateCommandHandler;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.services.UserService;

import java.util.Optional;

@Log4j
@Service
@RequiredArgsConstructor
public class MessageDistributor {

    private final UserService userService;

    private final WaitForHandleStateCommandHandler waitForHandleStateCommandHandler;

    private final WaitConfirmationCodeStateCommandHandler waitConfirmationCodeStateCommandHandler;

    private final BasicStateCommandHandler basicStateCommandHandler;

    private final WaitOpponentHandleStateCommandHandler waitOpponentHandleStateCommandHandler;

    private final WaitGameCreationStateCommandHandler waitGameCreationStateCommandHandler;

    private final DuringTheGameStateCommandHandler duringTheGameStateCommandHandler;

    public void distributeMessagesByType(@NotNull Update update) {
        var message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();

        User user = getUser(message);
        Long chatId = message.getChatId();
        String text = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();

        getCommandByUserState(user.getState())
                .orElseThrow(() -> new RuntimeException("Command not found"))
                .execute(user, chatId, text);
    }

    private @NotNull Optional<CommandHandler> getCommandByUserState(@NotNull UserState state) {
        return switch (state) {
            case WAIT_FOR_HANDLE_STATE -> Optional.of(waitForHandleStateCommandHandler);
            case WAIT_CONFIRMATION_CODE_STATE -> Optional.of(waitConfirmationCodeStateCommandHandler);
            case BASIC_STATE -> Optional.of(basicStateCommandHandler);
            case WAIT_OPPONENT_HANDLE_STATE -> Optional.of(waitOpponentHandleStateCommandHandler);
            case WAIT_GAME_CREATION_STATE -> Optional.of(waitGameCreationStateCommandHandler);
            case DURING_THE_GAME -> Optional.of(duringTheGameStateCommandHandler);
            default -> Optional.empty();
        };
    }

    private @NotNull User getUser(@NotNull Message message) {
        Long telegramId = message.getChatId();
        String firstName = message.getFrom().getFirstName();
        String lastName = message.getFrom().getLastName();
        String username = message.getFrom().getUserName();

        return userService.getUserByTelegramId(telegramId)
                .orElseGet(() -> {
                    User registratedUser = new User(telegramId, firstName, lastName, username);
                    userService.registerUser(registratedUser);
                    return registratedUser;
                });
    }

}
