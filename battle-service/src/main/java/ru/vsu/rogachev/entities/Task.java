package ru.vsu.rogachev.entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    @NotNull
    private GameSession game;

    @ManyToOne
    @JoinColumn(name = "solver_handle", referencedColumnName = "handle")
    @Column(name = "solver_handle")
    private Player solver;

    @Column(name = "task_url")
    @NotNull
    private String taskUrl;

    @Column(name = "time")
    private Long time;

    public Task(GameSession game, Player solver, String taskUrl, Long time) {
        this.game = game;
        this.solver = solver;
        this.taskUrl = taskUrl;
        this.time = time;
    }
}
