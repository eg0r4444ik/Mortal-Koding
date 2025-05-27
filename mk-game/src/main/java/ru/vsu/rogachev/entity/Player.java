package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.vsu.rogachev.entity.enums.PlayerState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "players")
public class Player {

    @Id
    @NotNull
    @EqualsAndHashCode.Include
    @Column(name = "handle", nullable = false)
    private String handle;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Long rating;

    @NotNull
    @Column(name = "state", nullable = false)
    private PlayerState state;

    @NotNull
    @JoinColumn(name = "game_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Game game;

}
