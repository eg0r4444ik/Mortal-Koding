package ru.vsu.rogachev.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.vsu.rogachev.entities.enums.GameState;
import ru.vsu.rogachev.entities.enums.PlayerState;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column(name = "state")
    private GameState state;

    @Column(name = "players_count")
    private Long playersCount;

    @Column(name = "tasks_count")
    private Long tasksCount;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "game_id")
    private List<Player> players;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "game_id")
    private List<Task> tasks;

    public GameSession(Long time, Long playersCount, Long tasksCount, GameState state) {
        this.time = time;
        this.playersCount = playersCount;
        this.tasksCount = tasksCount;
        this.state = state;
        players = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void addTasks(List<Task> tasks){
        for(Task task : tasks) {
            this.tasks.add(task);
        }
    }
}
