package ru.vsu.rogachev.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entity.ConfirmRequest;
import ru.vsu.rogachev.repositories.ConfirmRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmService {

    private final ConfirmRepository confirmRepository;

    public void add(@NotNull ConfirmRequest confirmRequest){
        confirmRepository.save(confirmRequest);
    }

    @NotNull
    public List<ConfirmRequest> getAll(){
        return confirmRepository.findAll();
    }

    @NotNull
    public Optional<ConfirmRequest> getById(@NotNull Long id){
        return confirmRepository.findById(id);
    }

    @NotNull
    public Optional<ConfirmRequest> getByEmail(@NotNull String email) {
        return confirmRepository.findByEmail(email);
    }

    public boolean existsById(@NotNull Long id) {
        return confirmRepository.existsById(id);
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
