package ru.vsu.rogachev.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "confirm")
public class ConfirmRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirm_id")
    private Long id;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @NotNull
    private User user;

    @Column(name = "confirmation_code")
    @NotNull
    private String confirmationCode;

    public ConfirmRequest(User user, String confirmationCode) {
        this.user = user;
        this.confirmationCode = confirmationCode;
    }
}
