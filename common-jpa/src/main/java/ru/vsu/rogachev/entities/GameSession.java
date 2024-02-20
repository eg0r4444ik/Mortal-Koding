package ru.vsu.rogachev.entities;

import com.sun.istack.NotNull;
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

    @JoinColumn(name = "first_user_id", referencedColumnName = "user_id")
    @NotNull
    private User firstUser;

    @JoinColumn(name = "second_user_id", referencedColumnName = "user_id")
    @NotNull
    private User secondUser;

    @OneToMany
    @JoinColumn(name = "game_id")
    private List<Task> tasks;

    public GameSession(Long time, User firstUser, User secondUser) {
        this.time = time;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }
}
