package ru.vsu.rogachev.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime creationDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "confirmation_code")
    private String confirmationCode;

}
