package ru.vsu.rogachev.service.distributor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vsu.rogachev.service.handler.CommandHandler;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.services.UserService;

import java.util.Map;
import java.util.Optional;

@Log4j
@Service
@RequiredArgsConstructor
public class MessageDistributor {

    private final Map<String, CommandHandler> handlers;

    private final UserService userService;

    public void distributeMessagesByType(@NotNull Update update) {
        var message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();

        User user = getUser(message);
        Long chatId = message.getChatId();
        String text = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();

        getHandlerByUserState(user.getState())
                .orElseThrow()
                .execute(user, chatId, text);
    }

    private @NotNull Optional<CommandHandler> getHandlerByUserState(@NotNull UserState state) {
        return handlers.values()
                .stream()
                .filter(handler -> handler.getHandledState() == state)
                .findFirst();
    }

    private @NotNull User getUser(@NotNull Message message) {
        Long telegramId = message.getChatId();
        String firstName = message.getFrom().getFirstName();
        String lastName = message.getFrom().getLastName();
        String username = message.getFrom().getUserName();

        return userService.getUserByChatId(telegramId)
                .orElseGet(() -> {
                    User registratedUser = new User(telegramId, firstName, lastName, username);
                    userService.registerUser(registratedUser);
                    return registratedUser;
                });
    }

}
