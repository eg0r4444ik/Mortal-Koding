package ru.vsu.rogachev.entities;

import com.sun.istack.NotNull;
import lombok.*;
import ru.vsu.rogachev.models.User;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    @NotNull
    private GameSession game;

    @Column(name = "solver_handle")
    private String solverHandle;

    @Column(name = "task_url")
    @NotNull
    private String taskUrl;

    @Column(name = "time")
    private Long time;

    public Task(GameSession game, String solverHandle, String taskUrl, Long time) {
        this.game = game;
        this.solverHandle = solverHandle;
        this.taskUrl = taskUrl;
        this.time = time;
    }
}
