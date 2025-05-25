package ru.vsu.rogachev.client.mk.game.dto.async;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.rogachev.client.mk.game.dto.async.enums.GameEventType;
import ru.vsu.rogachev.client.mk.game.dto.async.enums.GameType;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEvent {

    @NotNull
    @JsonProperty("creation_time")
    private LocalDate creationTime;

    @NotNull
    @JsonProperty("event_type")
    private GameEventType eventType;

    @NotNull
    @JsonProperty("originator_handle")
    private String originatorHandle;

    @NotNull
    @JsonProperty("originator_rating")
    private Long originatorRating;

    @NotNull
    @JsonProperty("parameters")
    private GameParameters parameters;

    @NotNull
    @JsonProperty("participants_handles")
    private List<String> participantsHandles;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameParameters {

        @NotNull
        @JsonProperty("duration")
        private Duration duration = Duration.ofMinutes(15);

        @NotNull
        @JsonProperty("game_type")
        private GameType type;

        @NotNull
        @JsonProperty("players_count")
        private Long playersCount;

        @NotNull
        @JsonProperty("tasks_count")
        private Long tasksCount;

    }

}
