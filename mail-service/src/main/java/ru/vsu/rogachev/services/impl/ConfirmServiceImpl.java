package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.repositories.ConfirmRepository;
import ru.vsu.rogachev.services.ConfirmService;

import java.util.List;
import java.util.Optional;

@Service
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    private ConfirmRepository confirmRepository;

    public void add(ConfirmRequest confirmRequest){
        confirmRepository.save(confirmRequest);
    }

    public void add(String email, String confirmationCode){
        ConfirmRequest confirmRequest = new ConfirmRequest(email, confirmationCode);
        confirmRepository.save(confirmRequest);
    }

    public List<ConfirmRequest> getAll(){
        return confirmRepository.findAll().stream().toList();
    }

    public ConfirmRequest getById(Long id){
        return confirmRepository.getById(id);
    }

    public ConfirmRequest getByEmail(String email) {
        Optional<ConfirmRequest> requests = confirmRepository.findByEmail(email);
        if(requests.isEmpty()){
            return null;
        }
        return confirmRepository.findByEmail(email).stream().toList().get(0);
    }

    @Transactional
    public void deleteById(Long id){
        confirmRepository.deleteById(id);
    }

    @Transactional
    public void deleteByEmail(String email){
        confirmRepository.deleteByEmail(email);
    }

    public boolean existsById(Long id) {
        return confirmRepository.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return confirmRepository.existsByEmail(email);
    }

}
