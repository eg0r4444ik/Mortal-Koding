package ru.vsu.rogachev.dto;

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

    public ConfirmDTO(ConfirmRequest confirmRequest) {
        this.id = confirmRequest.getId();
        this.user = new UserDTO(confirmRequest.getUser());
        this.confirmationCode = confirmRequest.getConfirmationCode();
        this.creationDate = confirmRequest.getCreationDate();
    }
}
