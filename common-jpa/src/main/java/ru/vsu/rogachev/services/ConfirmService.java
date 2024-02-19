package ru.vsu.rogachev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.repositories.ConfirmRepository;

@Service
public class ConfirmService {

    @Autowired
    private ConfirmRepository confirmRepository;

    public void add(ConfirmRequest confirmRequest){
        confirmRepository.save(confirmRequest);
    }

    public void add(Long userId, String confirmationCode){
        ConfirmRequest confirmRequest = new ConfirmRequest(userId, confirmationCode);
        confirmRepository.save(confirmRequest);
    }

    public ConfirmRequest getById(Long id){
        return confirmRepository.getById(id);
    }

    public ConfirmRequest getByUserId(Long id){
        return confirmRepository.findByUserId(id).get();
    }

    public void deleteById(Long id){
        confirmRepository.deleteById(id);
    }

    public void deleteByUserId(Long id){
        confirmRepository.deleteByUserId(id);
    }

}
