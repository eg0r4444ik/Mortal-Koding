package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(name = "telegram_id", unique = true, nullable = false)
    private Long telegramId;

    @Nullable
    @CreationTimestamp
    @Column(name = "first_login_date")
    private Date firstLoginDate;

    @NotNull
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Nullable
    @Column(name = "email", unique = true)
    private String email;

    @Nullable
    @Column(name = "rating", nullable = false)
    private Long rating;

    @Nullable
    @Column(name = "codeforces_username", unique = true)
    private String codeforcesUsername;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private UserState state = UserState.BASIC_STATE;

    public User(
            @NotNull Long telegramId,
            @NotNull String firstName,
            @NotNull String lastName,
            @NotNull String username
    ) {
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

}
