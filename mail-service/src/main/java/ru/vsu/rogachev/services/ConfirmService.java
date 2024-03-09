package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.ConfirmRequest;

import java.util.List;

public interface ConfirmService {

    void add(ConfirmRequest confirmRequest);

    void add(String email, String confirmationCode);

    List<ConfirmRequest> getAll();

    ConfirmRequest getById(Long id);

    ConfirmRequest getByEmail(String email);

    void deleteById(Long id);

    void deleteByEmail(String email);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

}
