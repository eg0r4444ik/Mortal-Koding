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
@Table(name = "confirms")
public class ConfirmRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirm_id")
    private Long id;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "confirmation_code")
    @NotNull
    private String confirmationCode;

    public ConfirmRequest(String email, String confirmationCode) {
        this.email = email;
        this.confirmationCode = confirmationCode;
    }
}
