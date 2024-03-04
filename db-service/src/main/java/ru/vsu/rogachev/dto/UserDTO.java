package ru.vsu.rogachev.dto;

import lombok.*;
import ru.vsu.rogachev.entities.User;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private Long telegramUserId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Long rating;
    private String codeforcesUsername;

    public UserDTO(User user) {
        this.id = user.getId();
        this.telegramUserId = user.getTelegramUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.rating = user.getRating();
        this.codeforcesUsername = user.getCodeforcesUsername();
    }
}
