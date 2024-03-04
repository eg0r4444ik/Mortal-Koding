package ru.vsu.rogachev.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.vsu.rogachev.entities.enums.UserState;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "telegram_user_id", unique = true)
    @NotNull
    private Long telegramUserId;

    @CreationTimestamp
    @Column(name = "first_login_date")
    private Date firstLoginDate;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "rating")
    private Long rating;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private GameSession game;

    @Column(name = "codeforces_username", unique = true)
    private String codeforcesUsername;

    @Column(name = "active")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private UserState state;

    public User(Long telegramUserId, String firstName, String lastName, String username,
                String email, Long rating, String codeforcesUsername, boolean isActive, UserState userState) {
        this.telegramUserId = telegramUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.rating = rating;
        this.codeforcesUsername = codeforcesUsername;
        this.isActive = isActive;
        this.state = userState;
    }
}
