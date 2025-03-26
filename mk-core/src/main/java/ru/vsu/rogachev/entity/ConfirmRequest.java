package ru.vsu.rogachev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "confirm_requests")
public class ConfirmRequest {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirm_id", nullable = false)
    private Long id;

    @NotNull
    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "confirmation_code", nullable = false)
    private String confirmationCode;

    public ConfirmRequest(@NotNull String email) {
        this.email = email;
    }

}
