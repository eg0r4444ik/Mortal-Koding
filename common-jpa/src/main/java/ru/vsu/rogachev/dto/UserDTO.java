package ru.vsu.rogachev.dto;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.vsu.rogachev.entities.GameSession;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull
    private Long telegramUserId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Long rating;
    private String codeforcesUsername;

}
