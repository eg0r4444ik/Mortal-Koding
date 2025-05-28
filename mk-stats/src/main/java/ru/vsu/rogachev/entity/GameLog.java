package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "game_logs")
public class GameLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_log_id")
    private Long id;

    @NotNull
    @CreationTimestamp
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @NotNull
    @Column(name = "players_handle")
    private String playersHandle;

    @NotNull
    @Column(name = "game_score")
    private Long gameScore;

    @NotNull
    @Column(name = "place")
    private Integer place;

    @NotNull
    @Column(name = "old_rating")
    private Long oldRating;

    @NotNull
    @Column(name = "new_rating")
    private Long newRating;

    @NotNull
    @Column(name = "performance")
    private Long performance;

    public GameLog(
            @Nullable LocalDateTime endTime,
            @NotNull String playersHandle,
            @NotNull Long gameScore,
            @NotNull Integer place,
            @NotNull Long oldRating,
            @NotNull Long newRating,
            @NotNull Long performance
    ) {
       this.endTime = endTime;
       this.playersHandle = playersHandle;
       this.gameScore = gameScore;
       this.place = place;
       this.oldRating = oldRating;
       this.newRating = newRating;
       this.performance = performance;
    }

}
