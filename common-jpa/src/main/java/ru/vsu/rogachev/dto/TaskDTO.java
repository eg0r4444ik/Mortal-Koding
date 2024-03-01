package ru.vsu.rogachev.dto;

import com.sun.istack.NotNull;
import lombok.*;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.User;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    @NotNull
    private GameSession game;

    private User solver;

    @NotNull
    private String taskUrl;

    private Long time;

}
