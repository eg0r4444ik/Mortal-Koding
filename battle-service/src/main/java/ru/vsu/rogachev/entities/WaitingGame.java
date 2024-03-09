package ru.vsu.rogachev.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "waiting_games")
public class WaitingGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_game_id")
    private Long id;

    @Column(name = "players_count")
    private Long playersCount;

    @OneToMany
    @JoinColumn(name = "waiting_game_id")
    private List<Player> players;

}
