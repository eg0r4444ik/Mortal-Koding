package ru.vsu.rogachev.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @CreationTimestamp
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "time")
    private Long time;

    @Column(name = "first_user_id")
    private Long firstUserId;

    @Column(name = "second_user_id")
    private Long secondUserId;

    @OneToMany
    @JoinColumn(name = "game_id")
    private List<Task> tasks;

    public GameSession(Long time, Long firstUserId, Long secondUserId) {
        this.time = time;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
    }
}
