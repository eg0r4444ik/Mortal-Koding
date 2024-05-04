package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.entities.enums.UserState;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;

public interface UserService {
    void addUser(Long telegramUserId, String firstName, String lastName, String username);

    void addUser(User user);

    User getUserById(Long id);

    User getUserByTelegramId(Long id) throws DbDontContainObjectException;

    User getUserByCodeforcesUsername(String username) throws DbDontContainObjectException;

    void setActive(User user, boolean active);

    void setEmail(User user, String email);

    void setCodeforcesUsername(User user, String codeforcesUsername);

    void setState(User user, UserState state);

    boolean existsById(Long id);

    boolean existsByTelegramId(Long id);

    boolean existsByCodeforcesUsername(String username);

    void changeUserState(Long id, UserState state);

    void changeUserActive(Long id);

    void changeCodeforcesUsername(Long id, String username);

    void deleteUserById(Long id);

    void deleteUserByTelegramId(Long id);

    void deleteUserByCodeforcesUsername(String username);
}
