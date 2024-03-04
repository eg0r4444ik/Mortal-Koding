package ru.vsu.rogachev.db.dto;

import lombok.*;

import java.util.Date;

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
    private Date creationDate;

    public ConfirmDTO(UserDTO user, String confirmationCode) {
        this.user = user;
        this.confirmationCode = confirmationCode;
    }
}
