package ru.vsu.rogachev.client.mk.stats.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticResponse {

    @NotNull
    @JsonProperty("game_results")
    private List<GameResult> gameResults;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameResult {

        @NotNull
        @JsonProperty("game_date_time")
        private LocalDateTime gameDateTime;

        @NotNull
        @JsonProperty("place")
        private Integer place;

        @NotNull
        @JsonProperty("score")
        private Long score;

        @NotNull
        @JsonProperty("old_rating")
        private Long oldRating;

        @NotNull
        @JsonProperty("new_rating")
        private Long newRating;

        @NotNull
        @JsonProperty("performance")
        private Long performance;

    }

}
