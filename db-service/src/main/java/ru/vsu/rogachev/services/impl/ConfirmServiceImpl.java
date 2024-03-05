package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.repositories.ConfirmRepository;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.UserService;

import java.util.List;

@Service
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    private ConfirmRepository confirmRepository;
    @Autowired
    private UserService userService;

    public void add(ConfirmRequest confirmRequest){
        confirmRepository.save(confirmRequest);
    }

    public void add(Long userId, String confirmationCode){
        ConfirmRequest confirmRequest = new ConfirmRequest(userService.getUserById(userId), confirmationCode);
        confirmRepository.save(confirmRequest);
    }

    public void add(User user, String confirmationCode){
        ConfirmRequest confirmRequest = new ConfirmRequest(user, confirmationCode);
        confirmRepository.save(confirmRequest);
    }

    public List<ConfirmRequest> getAll(){
        return confirmRepository.findAll().stream().toList();
    }

    public ConfirmRequest getById(Long id){
        return confirmRepository.getById(id);
    }

    public ConfirmRequest getByUserId(Long id) throws DbDontContainObjectException {
        if(confirmRepository.findByUserId(id).isEmpty()){
            throw new DbDontContainObjectException();
        };
        return confirmRepository.findByUserId(id).get();
    }

    @Transactional
    public void deleteById(Long id){
        confirmRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUserId(Long id){
        confirmRepository.deleteByUserId(id);
    }

    public boolean existById(Long id) {
        return confirmRepository.existsById(id);
    }

    public boolean existByUserId(Long id) {
        return confirmRepository.existsByUserId(id);
    }

}
