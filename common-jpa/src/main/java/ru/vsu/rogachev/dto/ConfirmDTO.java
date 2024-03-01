package ru.vsu.rogachev.dto;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.vsu.rogachev.entities.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmDTO {

    @NotNull
    private User user;

    @NotNull
    private String confirmationCode;

}
