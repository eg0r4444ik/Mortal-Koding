package ru.vsu.rogachev.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmDTO {

    private Long id;
    private UserDTO user;
    private String confirmationCode;

}
