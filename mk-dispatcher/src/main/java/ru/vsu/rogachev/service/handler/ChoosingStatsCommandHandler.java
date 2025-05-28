package ru.vsu.rogachev.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.vsu.rogachev.client.MkStatsClient;
import ru.vsu.rogachev.client.mk.stats.dto.UserStatisticResponse;
import ru.vsu.rogachev.controller.TelegramBot;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.service.UserService;
import ru.vsu.rogachev.service.message.BasicStateMessageSender;
import ru.vsu.rogachev.service.message.ChoosingStatsStateMessageSender;
import ru.vsu.rogachev.utils.ChartUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.vsu.rogachev.entity.enums.UserState.BASIC_STATE;
import static ru.vsu.rogachev.entity.enums.UserState.CHOOSING_STATS;
import static ru.vsu.rogachev.exception.BusinessLogicExceptions.UNKNOWN_COMMAND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChoosingStatsCommandHandler implements CommandHandler {

    private static final String EXIT_STATS_CHOOSING_MESSAGE = "Просмотр статистики закончен, вы можете выбрать одно из следующих действий";

    private final ChoosingStatsStateMessageSender choosingStatsStateMessageSender;

    private final BasicStateMessageSender basicStateMessageSender;

    private final MkStatsClient statsClient;

    private final UserService userService;

    private final TelegramBot telegramBot;

    @Getter
    @AllArgsConstructor
    public enum ProcessedCommand {
        EXIT_COMMAND("exit", "Отмена приглашения в игру"),
        RATING_CHANGES("rating_changes", "Изменения рейтинга"),
        SCORE_CHANGES("score_changes", "Изменения набранных за игру очков"),
        PERFORMANCE_CHANGES("performance_changes", "Изменения перфоманса"),
        UNKNOWN("unknown", "Неизвестная команда");

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
                .orElse(ProcessedCommand.UNKNOWN);

        switch (command) {
            case EXIT_COMMAND -> {
                userService.setUserState(user, BASIC_STATE);
                basicStateMessageSender.sendMessage(chatId, EXIT_STATS_CHOOSING_MESSAGE);
            }
            case RATING_CHANGES -> {
                showStats(user, ChartUtils.StatType.RATING);
            }
            case SCORE_CHANGES -> {
                showStats(user, ChartUtils.StatType.SCORE);
            }
            case PERFORMANCE_CHANGES -> {
                showStats(user, ChartUtils.StatType.PERFORMANCE);
            }
            case UNKNOWN -> throw BusinessLogicException.of(chatId, UNKNOWN_COMMAND);
        }
    }

    @Override
    public UserState getHandledState() {
        return CHOOSING_STATS;
    }

    private void showStats(@NotNull User user, @NotNull ChartUtils.StatType statType) {
        try {
            UserStatisticResponse stats = getStats(user);
            telegramBot.execute(ChartUtils.getStatsGraphic(user.getChatId(), stats, statType));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @NotNull
    private UserStatisticResponse getStats(@NotNull User user) {
        return statsClient.getUserStats(Objects.requireNonNull(user.getCodeforcesUsername()));
    }

}
