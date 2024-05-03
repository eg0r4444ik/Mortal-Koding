package ru.vsu.rogachev.entities.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequestDTO {
    private String email;
    private String confirmationCode;
}
