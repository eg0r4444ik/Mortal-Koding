package ru.vsu.rogachev.service.message;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vsu.rogachev.controller.TelegramBot;
import ru.vsu.rogachev.utils.CommonMessageUtils;

import java.util.List;

import static ru.vsu.rogachev.service.handler.BasicStateCommandHandler.ProcessedCommand.FIND_GAME_COMMAND;
import static ru.vsu.rogachev.service.handler.BasicStateCommandHandler.ProcessedCommand.LOOK_RATING_COMMAND;
import static ru.vsu.rogachev.service.handler.BasicStateCommandHandler.ProcessedCommand.LOOK_STATS;
import static ru.vsu.rogachev.service.handler.BasicStateCommandHandler.ProcessedCommand.PLAY_WITH_FRIEND_COMMAND;

@Service
@RequiredArgsConstructor
public class BasicStateMessageSender implements MessageSender {

    public static final List<String> BASIC_STATE_BUTTON_TEXTS =
            List.of("Поиск соперника", "Игра с другом", "Рейтинг", "Статистика");

    public static final List<String> BASIC_STATE_BUTTON_CALLBACK_DATA =
            List.of(FIND_GAME_COMMAND.getMessage(), PLAY_WITH_FRIEND_COMMAND.getMessage(), LOOK_RATING_COMMAND.getMessage(), LOOK_STATS.getMessage());

    private final TelegramBot telegramBot;

    @Override
    public void sendMessage(@NotNull Long chatId, @NotNull String text) {
        telegramBot.sendAnswerMessage(CommonMessageUtils.createMessageWithButtons(chatId, text, BASIC_STATE_BUTTON_TEXTS, BASIC_STATE_BUTTON_CALLBACK_DATA));
    }

    @Override
    public void sendMessage(@NotNull SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(CommonMessageUtils.createMessageWithButtons(sendMessage, BASIC_STATE_BUTTON_TEXTS, BASIC_STATE_BUTTON_CALLBACK_DATA));
    }
}
