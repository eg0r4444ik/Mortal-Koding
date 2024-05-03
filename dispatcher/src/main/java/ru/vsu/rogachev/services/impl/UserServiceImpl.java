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

    @Override
    public void addUser(Long telegramId, String firstName, String lastName, String username){
        if(!existsByTelegramId(telegramId)){
            User user = new User(telegramId, firstName, lastName, username, null, 0L,
                    null, false, UserState.BASIC_STATE);
            userRepository.save(user);
        }
    }

    @Override
    public void addUser(User user){
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id){
        return userRepository.getById(id);
    }

    @Override
    public User getUserByTelegramId(Long id) throws DbDontContainObjectException {
        if(!existsByTelegramId(id)){
            throw new DbDontContainObjectException();
        }
        return userRepository.findByTelegramId(id).get();
    }

    @Override
    public User getUserByCodeforcesUsername(String username) throws DbDontContainObjectException {
        if(!existsByCodeforcesUsername(username)){
            throw new DbDontContainObjectException();
        }
        return userRepository.findByCodeforcesUsername(username).get();
    }

    @Override
    public void setEmail(User user, String email) {
        user.setEmail(email);
        userRepository.save(user);
    }

    @Override
    public void setCodeforcesUsername(User user, String codeforcesUsername) {
        user.setCodeforcesUsername(codeforcesUsername);
        userRepository.save(user);
    }

    @Override
    public void setState(User user, UserState state) {
        user.setState(state);
        userRepository.save(user);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByTelegramId(Long id) {
        return userRepository.existsByTelegramId(id);
    }

    @Override
    public boolean existsByCodeforcesUsername(String username) {
        return userRepository.existsByCodeforcesUsername(username);
    }

    @Override
    public void changeUserState(Long id, UserState state){
        User user = userRepository.getById(id);
        user.setState(state);
        userRepository.save(user);
    }

    @Override
    public void changeUserActive(Long id){
        User user = userRepository.getById(id);
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    @Override
    public void changeCodeforcesUsername(Long id, String username){
        User user = userRepository.getById(id);
        user.setCodeforcesUsername(username);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUserByTelegramId(Long id){
        userRepository.deleteByTelegramId(id);
    }

    @Override
    @Transactional
    public void deleteUserByCodeforcesUsername(String username){
        userRepository.deleteByCodeforcesUsername(username);
    }

}
