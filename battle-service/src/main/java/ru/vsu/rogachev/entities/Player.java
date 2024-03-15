package ru.vsu.rogachev.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import ru.vsu.rogachev.entities.enums.PlayerState;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private PlayerState state;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private GameSession game;

    public Player(String handle, String email, long rating, PlayerState state) {
        this.handle = handle;
        this.email = email;
        this.rating = rating;
        this.state = state;
    }
}
