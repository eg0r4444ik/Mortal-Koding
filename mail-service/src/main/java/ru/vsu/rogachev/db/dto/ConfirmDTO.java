package ru.vsu.rogachev.db.dto;

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

    public ConfirmDTO(UserDTO user, String confirmationCode) {
        this.user = user;
        this.confirmationCode = confirmationCode;
    }
}
