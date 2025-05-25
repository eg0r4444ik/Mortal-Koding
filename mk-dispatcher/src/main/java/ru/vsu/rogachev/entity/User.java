package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vsu.rogachev.entity.enums.UserState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Nullable
    @CreationTimestamp
    @Column(name = "first_login_date")
    private LocalDate firstLoginDate;

    @NotNull
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Nullable
    @Column(name = "lastname")
    private String lastName;

    @Nullable
    @Column(name = "username")
    private String username;

    @Nullable
    @Column(name = "email", unique = true)
    private String email;

    @Nullable
    @Column(name = "rating")
    private Long rating;

    @Nullable
    @Column(name = "codeforces_username", unique = true)
    private String codeforcesUsername;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean isActive = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private UserState state = UserState.BASIC_STATE;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "user_invites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "invite_id")
    )
    private List<Invite> invites = new ArrayList<>();

    public User(
            @NotNull Long chatId,
            @NotNull String firstName,
            @Nullable String lastName,
            @Nullable String username
    ) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

}
