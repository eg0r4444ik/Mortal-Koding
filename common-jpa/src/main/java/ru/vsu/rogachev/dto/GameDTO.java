package ru.vsu.rogachev.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.vsu.rogachev.entities.Task;
import ru.vsu.rogachev.entities.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private Long time;
    private Long playersCount;

}
