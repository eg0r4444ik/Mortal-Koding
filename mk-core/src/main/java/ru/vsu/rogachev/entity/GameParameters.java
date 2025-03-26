package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.vsu.rogachev.entity.enums.GameType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Duration;

@Data
@Entity
@NoArgsConstructor
@Table(name = "game_parameters")
public class GameParameters {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_parameters_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Duration duration = Duration.ofMinutes(15);

    @NotNull
    @Column(name = "game_type", nullable = false)
    private GameType type;

    @NotNull
    @Column(name = "players_count", nullable = false)
    private Long playersCount;

    @NotNull
    @Column(name = "tasks_count", nullable = false)
    private Long tasksCount;

}
