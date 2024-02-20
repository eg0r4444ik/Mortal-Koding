package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.entities.enums.UserState;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;

public interface UserService {
    void addUser(Long telegramUserId, String firstName, String lastName, String username,
                        String email, String codeforcesUsername);

    void addUser(User user);

    User getUserById(Long id);

    User getUserByTelegramId(Long id) throws DbDontContainObjectException;

    User getUserByCodeforcesUsername(String username) throws DbDontContainObjectException;

    void changeUserState(Long id, UserState state);

    void changeUserActive(Long id);

    void changeCodeforcesUsername(Long id, String username);

    void deleteUserById(Long id);

    void deleteUserByTelegramId(Long id);

    void deleteUserByCodeforcesUsername(String username);
}
