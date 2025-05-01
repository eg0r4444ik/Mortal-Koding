package ru.vsu.rogachev.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.client.codeforces.dto.CodeforcesUser;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.UserState;
import ru.vsu.rogachev.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(@NotNull User user){
        userRepository.save(user);
    }

    public @NotNull Optional<User> getUserByChatId(@NotNull Long id) {
        return userRepository.findByChatId(id);
    }

    public void setCodeforcesInfo(@NotNull User user, @NotNull CodeforcesUser codeforcesUser) {
        user.setEmail(codeforcesUser.getEmail());
        user.setCodeforcesUsername(codeforcesUser.getHandle());
        userRepository.save(user);
    }

    public void setUserState(@NotNull User user, @NotNull UserState userState) {
        user.setState(userState);
        userRepository.save(user);
    }

    public void activateUser(@NotNull User user) {
        user.setIsActive(true);
        userRepository.save(user);
    }

    public @NotNull Optional<User> getUserById(@NotNull Long id){
        return userRepository.findById(id);
    }

    public @NotNull Optional<User> getUserByCodeforcesUsername(@NotNull String username) {
        return userRepository.findByCodeforcesUsername(username);
    }

    public boolean existsById(@NotNull Long id) {
        return userRepository.existsById(id);
    }

    public boolean existsByChatId(@NotNull Long id) {
        return userRepository.existsByChatId(id);
    }

    public boolean existsByCodeforcesUsername(@NotNull String username) {
        return userRepository.existsByCodeforcesUsername(username);
    }

    @Transactional
    public void deleteUserById(@NotNull Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserByChatId(@NotNull Long id){
        userRepository.deleteByChatId(id);
    }

    @Transactional
    public void deleteUserByCodeforcesUsername(@NotNull String username){
        userRepository.deleteByCodeforcesUsername(username);
    }

}
