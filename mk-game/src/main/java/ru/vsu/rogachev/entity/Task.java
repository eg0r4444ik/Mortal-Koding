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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @NotNull
    @Column(name = "task_url", nullable = false)
    private String taskUrl;

    @NotNull
    @Column(name = "rating")
    private Long rating;

    @NotNull
    @Column(name = "serial_number")
    private Long serialNumber;

    @NotNull
    @JoinColumn(name = "game_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Game game;

    @Nullable
    @OneToOne(fetch = FetchType.EAGER)
    private Player solver;

    @Nullable
    @Column(name = "solve_time")
    private LocalDateTime solveTime;

    public void setSolver(@NotNull Player solver) {
        this.solver = solver;
        this.solveTime = LocalDateTime.now();
    }

}
