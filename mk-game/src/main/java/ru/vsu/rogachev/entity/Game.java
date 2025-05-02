package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vsu.rogachev.entity.enums.GameState;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @Nullable
    @CreationTimestamp
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NotNull
    @Column(name = "state")
    private GameState state;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private GameParameters parameters;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Player> players = new HashSet<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Game(
            @Nullable LocalDateTime startTime,
            @NotNull GameState state,
            @NotNull GameParameters parameters
    ) {
        this.startTime = startTime;
        this.state = state;
        this.parameters = parameters;
    }

    public void addPlayer(@NotNull Player player) {
        players.add(player);
    }

}
