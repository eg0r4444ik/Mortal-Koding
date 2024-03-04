package ru.vsu.rogachev.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private GameDTO game;
    private UserDTO solver;
    private String taskUrl;
    private Long time;

}
