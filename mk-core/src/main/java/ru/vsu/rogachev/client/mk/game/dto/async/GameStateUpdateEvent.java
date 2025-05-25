package ru.vsu.rogachev.client.mk.game.dto.async;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.rogachev.client.mk.game.dto.CurrentGameState;
import ru.vsu.rogachev.client.mk.game.dto.async.enums.GameStateUpdateEventType;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStateUpdateEvent {

    @NotNull
    @JsonProperty("state")
    private CurrentGameState state;

    @NotNull
    @JsonProperty("event_type")
    private GameStateUpdateEventType eventType;

    @NotNull
    @JsonProperty("recalculated_ratings")
    private List<PlayersRating> recalculatedRatings;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayersRating {

        @NotNull
        @JsonProperty("player_handle")
        private String playerHandle;

        @NotNull
        @JsonProperty("old_rating")
        private Long oldRating;

        @NotNull
        @JsonProperty("new_rating")
        private Long newRating;

    }

}
