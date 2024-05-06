package ru.vsu.rogachev.entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invite")
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    private Long id;

    @Column(name = "sender", unique = true)
    @NotNull
    private Long senderTelegramId;

    @Column(name = "recipient", unique = true)
    @NotNull
    private Long recipientTelegramId;

    public Invite(Long senderTelegramId, Long recipientTelegramId) {
        this.senderTelegramId = senderTelegramId;
        this.recipientTelegramId = recipientTelegramId;
    }
}
