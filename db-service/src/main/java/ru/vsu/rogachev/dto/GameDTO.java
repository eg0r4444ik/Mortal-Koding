package ru.vsu.rogachev.dto;

import lombok.*;
import ru.vsu.rogachev.entities.GameSession;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private Long id;
    private Long time;
    private Long playersCount;

    public GameDTO(GameSession gameSession) {
        this.id = gameSession.getId();
        this.time = gameSession.getTime();
        this.playersCount = gameSession.getPlayersCount();
    }
}
