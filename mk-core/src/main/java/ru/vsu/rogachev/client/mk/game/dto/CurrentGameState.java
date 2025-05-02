package ru.vsu.rogachev.client.mk.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentGameState {

    @NotNull
    @JsonProperty("players_scores")
    private List<PlayersScore> playersScores;

    @NotNull
    @JsonProperty("time_until_end")
    private Duration timeUntilEnd;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayersScore {

        @NotNull
        @JsonProperty("task_serial_number")
        private Long taskSerialNumber;

        @NotNull
        @JsonProperty("player_handle")
        private String playerHandle;

        @NotNull
        @JsonProperty("score")
        private Long score;

    }

}
