package ru.vsu.rogachev.service.message;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vsu.rogachev.controller.TelegramBot;
import ru.vsu.rogachev.utils.CommonMessageUtils;

import java.util.List;

import static ru.vsu.rogachev.service.handler.ChoosingStatsCommandHandler.ProcessedCommand.PERFORMANCE_CHANGES;
import static ru.vsu.rogachev.service.handler.ChoosingStatsCommandHandler.ProcessedCommand.RATING_CHANGES;
import static ru.vsu.rogachev.service.handler.ChoosingStatsCommandHandler.ProcessedCommand.SCORE_CHANGES;

@Service
@RequiredArgsConstructor
public class ChoosingStatsStateMessageSender implements MessageSender {

    public static final List<String> CHOOSING_STATS_STATE_BUTTON_TEXTS =
            List.of("Изменения рейтинга", "Количество набранных очков", "Перфоманс");

    public static final List<String> CHOOSING_STATS_STATE_BUTTON_CALLBACK_DATA =
            List.of(RATING_CHANGES.getMessage(), SCORE_CHANGES.getMessage(), PERFORMANCE_CHANGES.getMessage());

    private final TelegramBot telegramBot;

    @Override
    public void sendMessage(@NotNull Long chatId, @NotNull String text) {
        telegramBot.sendAnswerMessage(
                CommonMessageUtils.createMessageWithButtons(
                        chatId,
                        text,
                        CHOOSING_STATS_STATE_BUTTON_TEXTS,
                        CHOOSING_STATS_STATE_BUTTON_CALLBACK_DATA
                )
        );
    }

    @Override
    public void sendMessage(@NotNull SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(
                CommonMessageUtils.createMessageWithButtons(
                        sendMessage,
                        CHOOSING_STATS_STATE_BUTTON_TEXTS,
                        CHOOSING_STATS_STATE_BUTTON_CALLBACK_DATA
                )
        );
    }
}
