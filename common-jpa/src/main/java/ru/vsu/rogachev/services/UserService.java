package ru.vsu.rogachev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.entities.enums.UserState;
import ru.vsu.rogachev.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(Long telegramUserId, String firstName, String lastName, String username,
                        String email, String codeforcesUsername){
        User user = new User(telegramUserId, firstName, lastName, username, email, codeforcesUsername);
        userRepository.save(user);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.getById(id);
    }

    public User getUserByTelegramId(Long id){
        return userRepository.findUserByTelegramUserId(id).get();
    }

    public User getUserByCodeforcesUsername(String username){
        return userRepository.findUserByCodeforcesUsername(username).get();
    }

    public void changeUserState(Long id, UserState state){
        User user = userRepository.getById(id);
        user.setState(state);
        userRepository.save(user);
    }

    public void changeUserActive(Long id){
        User user = userRepository.getById(id);
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    public void changeCodeforcesUsername(Long id, String username){
        User user = userRepository.getById(id);
        user.setCodeforcesUsername(username);
        userRepository.save(user);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public void deleteUserByTelegramId(Long id){
        userRepository.deleteByTelegramUserId(id);
    }

    public void deleteUserByCodeforcesUsername(String username){
        userRepository.findUserByCodeforcesUsername(username);
    }

}
