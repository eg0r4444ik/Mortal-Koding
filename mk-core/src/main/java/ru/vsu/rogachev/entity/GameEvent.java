package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vsu.rogachev.entity.enums.GameEventType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Setter
@NoArgsConstructor
@Table(name = "game_events")
public class GameEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_event_id")
    private Long id;

    @NotNull
    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDate creationTime;

    @Column(name = "processed")
    private boolean processed;

    @NotNull
    @Column(name = "event_type", nullable = false)
    private GameEventType eventType;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private User originator;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    private GameParameters gameParameters;

    @Nullable
    @OneToOne(fetch = FetchType.EAGER)
    private User participant;

    public GameEvent(@NotNull GameEventType eventType, @NotNull User originator) {
        this.eventType = eventType;
        this.originator = originator;
    }

}
