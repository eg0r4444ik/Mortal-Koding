package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "task_url", nullable = false)
    private String taskUrl;

    @NotNull
    @Column(name = "rating")
    private Long rating;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private Game game;

    @Nullable
    @OneToOne(fetch = FetchType.EAGER)
    private Player solver;

    @Nullable
    @Column(name = "solve_time")
    private Date solve_time;

}
