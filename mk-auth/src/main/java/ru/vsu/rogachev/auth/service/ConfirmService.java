package ru.vsu.rogachev.auth.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.auth.entity.ConfirmRequest;
import ru.vsu.rogachev.auth.repository.ConfirmRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmService {

    private final ConfirmRepository confirmRepository;

    public void add(@NotNull ConfirmRequest confirmRequest){
        confirmRepository.save(confirmRequest);
    }

    public void add(@NotNull String email, @NotNull String activationCode){
        confirmRepository.save(new ConfirmRequest(LocalDate.now(), email, activationCode));
    }

    @NotNull
    public List<ConfirmRequest> getAll(){
        return confirmRepository.findAll();
    }


    @NotNull
    public Optional<ConfirmRequest> getByEmail(@NotNull String email) {
        return confirmRepository.findByEmail(email);
    }

    public boolean existsByEmail(@NotNull String email) {
        return confirmRepository.existsByEmail(email);
    }

    @Transactional
    public void deleteById(@NotNull Long id){
        confirmRepository.deleteById(id);
    }

    @Transactional
    public void deleteByEmail(@NotNull String email){
        confirmRepository.deleteByEmail(email);
    }

}
