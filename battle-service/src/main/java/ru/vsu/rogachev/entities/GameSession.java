package ru.vsu.rogachev.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.vsu.rogachev.models.User;

import javax.persistence.*;
import java.util.Date;
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
    private Date startTime;

    @Column(name = "time")
    private Long time;

    @Column(name = "first_user_handle")
    private String firstUserHandle;

    @Column(name = "second_user_handle")
    private String secondUserHandle;

    @OneToMany
    @JoinColumn(name = "game_id")
    private List<Task> tasks;

    public GameSession(Long time) {
        this.time = time;
    }
}
