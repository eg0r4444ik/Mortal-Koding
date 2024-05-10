package ru.vsu.rogachev.entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "task_number")
    private Integer numberInGame;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    @NotNull
    private GameSession game;

    @ManyToOne
    @JoinColumn(name = "solver_handle", referencedColumnName = "handle")
    private Player solver;

    @Column(name = "task_url")
    @NotNull
    private String taskUrl;

    @Column(name = "time")
    private Date time;

    public Task(GameSession game, String taskUrl, Integer numberInGame) {
        this.game = game;
        this.taskUrl = taskUrl;
        this.numberInGame = numberInGame;
    }
}
