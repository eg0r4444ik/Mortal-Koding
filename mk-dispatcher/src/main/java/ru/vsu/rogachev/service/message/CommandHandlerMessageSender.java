package ru.vsu.rogachev.service.message;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vsu.rogachev.controller.TelegramBot;
import ru.vsu.rogachev.entity.CommandHandlerButtons;
import ru.vsu.rogachev.utils.CommonMessageUtils;

import java.util.Collections;

@Setter
@Service
@RequiredArgsConstructor
public class CommandHandlerMessageSender implements MessageSender {

    private final TelegramBot telegramBot;

    private CommandHandlerButtons commandHandlerButtons = CommandHandlerButtons.builder()
            .buttonText(Collections.emptyList())
            .buttonCallbackData(Collections.emptyList())
            .build();

    @Override
    public void sendMessage(@NotNull Long chatId, @NotNull String text) {
        telegramBot.sendAnswerMessage(
                CommonMessageUtils.createMessageWithButtons(
                        chatId,
                        text,
                        commandHandlerButtons.getButtonText(),
                        commandHandlerButtons.getButtonCallbackData()
                )
        );
    }

    @Override
    public void sendMessage(@NotNull SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(
                CommonMessageUtils.createMessageWithButtons(
                        sendMessage,
                        commandHandlerButtons.getButtonText(),
                        commandHandlerButtons.getButtonCallbackData()
                )
        );
    }

}
