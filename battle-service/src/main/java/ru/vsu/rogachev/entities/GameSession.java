package ru.vsu.rogachev.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.vsu.rogachev.entities.enums.PlayerState;

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
@Table(name = "games")
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

    @Column(name = "started")
    private boolean isStarted;

    @Column(name = "players_count")
    private Long playersCount;

    @OneToMany
    @JoinColumn(name = "game_id")
    private List<Player> players;

    @OneToMany
    @JoinColumn(name = "game_id")
    private List<Task> tasks;

    public GameSession(Long time, Long playersCount) {
        this.time = time;
        this.playersCount = playersCount;
    }

    public void addPlayer(Player player){
        player.setState(PlayerState.IN_GAME);
        players.add(player);
    }
}
