package ru.vsu.rogachev.client.mk.game.dto.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.rogachev.client.mk.game.dto.CurrentGameState;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGameStateResponse {

    @NotNull
    @JsonProperty("state")
    private CurrentGameState state;

}
