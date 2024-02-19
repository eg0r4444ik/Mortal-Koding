package ru.vsu.rogachev.entities;

import lombok.*;

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

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "solver_id")
    private Long solverId;

    @Column(name = "task_url")
    private String taskUrl;

    @Column(name = "time")
    private Long time;

}
