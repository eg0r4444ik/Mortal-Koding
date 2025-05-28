package ru.vsu.rogachev.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vsu.rogachev.client.mk.game.dto.CurrentGameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@UtilityClass
public class GameMessageUtils {

    private static final String GAME_STATE_TABLE_HEADER = "Состояние соревнования:";
    private static final String GAME_RESULT_TABLE_HEADER = "Игра окончена! Результаты представлены в таблице";
    private static final String RATING_CHANGES_MESSAGE = "Ваш рейтинг поменялся! %s -> %s";

    public SendMessage getGameStateMessage(
            @NotNull Long chatId,
            @NotNull CurrentGameState state
    ) {
        return CommonMessageUtils.createMessageWithTable(chatId, GAME_STATE_TABLE_HEADER, mapGameStateToTable(state));
    }

    public SendMessage getGameTasks(@NotNull Long chatId, @NotNull List<String> problems){
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());

        StringBuilder taskList = new StringBuilder();
        taskList.append("Задачи соревнования: \n");
        int curr = 1;

        for (String url : problems) {
            taskList.append("Задача ").append(curr).append(": ").append(url).append("\n");
            curr++;
        }

        sendMessage.setText(taskList.toString());

        return sendMessage;
    }

    public SendMessage getEndGameMessage(@NotNull Long chatId, @NotNull CurrentGameState state){
        return CommonMessageUtils.createMessageWithTable(chatId, GAME_RESULT_TABLE_HEADER, mapGameStateToTable(state));
    }

    public SendMessage getRatingChangesMessage(@NotNull Long chatId, @NotNull Long oldRating, @NotNull Long newRating){
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format(RATING_CHANGES_MESSAGE, oldRating, newRating));

        return sendMessage;
    }

    @NotNull
    private List<List<String>> mapGameStateToTable(@NotNull CurrentGameState state) {
        List<CurrentGameState.PlayersScore> scores = state.getPlayersScores();

        Map<Long, Map<String, Long>> taskPlayerScores = new HashMap<>();
        Set<String> playerHandles = new HashSet<>();

        for (CurrentGameState.PlayersScore score : scores) {
            String player = score.getPlayerHandle();
            Long task = score.getTaskSerialNumber();
            Long points = score.getScore();

            playerHandles.add(player);

            taskPlayerScores
                    .computeIfAbsent(task, k -> new HashMap<>())
                    .put(player, points);
        }

        List<String> sortedPlayers = new ArrayList<>(playerHandles);
        sortedPlayers.sort(String::compareTo);

        List<Long> sortedTasks = new ArrayList<>(taskPlayerScores.keySet());
        Collections.sort(sortedTasks);

        List<List<String>> table = new ArrayList<>();

        List<String> header = new ArrayList<>();
        header.add("Задача");
        header.addAll(sortedPlayers);
        table.add(header);

        for (Long task : sortedTasks) {
            List<String> row = new ArrayList<>();
            row.add("Задача " + task);

            Map<String, Long> scoresForTask = taskPlayerScores.getOrDefault(task, Map.of());

            for (String player : sortedPlayers) {
                row.add(String.valueOf(scoresForTask.getOrDefault(player, 0L)));
            }

            table.add(row);
        }

        List<String> totalRow = new ArrayList<>();
        totalRow.add("Сумма");

        for (String player : sortedPlayers) {
            long sum = sortedTasks.stream()
                    .map(task -> taskPlayerScores.getOrDefault(task, Map.of()).getOrDefault(player, 0L))
                    .mapToLong(Long::longValue)
                    .sum();
            totalRow.add(String.valueOf(sum));
        }

        table.add(totalRow);

        return table;
    }

}
