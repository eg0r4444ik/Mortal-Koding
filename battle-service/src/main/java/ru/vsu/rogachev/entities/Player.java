package ru.vsu.rogachev.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @Column(name = "handle")
    private String handle;

    @Column(name = "email")
    private String email;

    @Column(name = "rating")
    private long rating;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private GameSession game;

    @ManyToOne
    @JoinColumn(name = "waiting_game_id", referencedColumnName = "waiting_game_id")
    private WaitingGame waitingGame;

    public Player(String handle, String email, long rating) {
        this.handle = handle;
        this.email = email;
        this.rating = rating;
    }
}
