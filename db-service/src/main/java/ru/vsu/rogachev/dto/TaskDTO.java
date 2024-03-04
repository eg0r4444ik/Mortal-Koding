package ru.vsu.rogachev.dto;

import lombok.*;
import ru.vsu.rogachev.entities.Task;

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

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.game = new GameDTO(task.getGame());
        this.solver = new UserDTO(task.getSolver());
        this.taskUrl = task.getTaskUrl();
        this.time = task.getTime();
    }
}
