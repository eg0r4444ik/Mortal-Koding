package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "players")
public class Player {

    @Id
    @NotNull
    @Column(name = "handle", nullable = false)
    private String handle;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private Game game;

}
