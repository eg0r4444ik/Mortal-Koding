package ru.vsu.rogachev.dto;

import lombok.*;

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

}
