package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.entities.enums.UserState;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.repositories.UserRepository;
import ru.vsu.rogachev.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(Long telegramUserId, String firstName, String lastName, String username,
                        String email, Long rating, String codeforcesUsername){
        User user = new User(telegramUserId, firstName, lastName, username, email, rating,
                codeforcesUsername, false, UserState.BASIC_STATE);
        userRepository.save(user);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.getById(id);
    }

    public User getUserByTelegramId(Long id) throws DbDontContainObjectException {
        if(userRepository.findUserByTelegramUserId(id).isEmpty()){
            throw new DbDontContainObjectException();
        }
        return userRepository.findUserByTelegramUserId(id).get();
    }

    public User getUserByCodeforcesUsername(String username) throws DbDontContainObjectException {
        if(userRepository.findUserByCodeforcesUsername(username).isEmpty()){
            throw new DbDontContainObjectException();
        }
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

    @Transactional
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserByTelegramId(Long id){
        userRepository.deleteByTelegramUserId(id);
    }

    @Transactional
    public void deleteUserByCodeforcesUsername(String username){
        userRepository.findUserByCodeforcesUsername(username);
    }

}
