package ru.vsu.rogachev.dto;

import lombok.*;

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

}
