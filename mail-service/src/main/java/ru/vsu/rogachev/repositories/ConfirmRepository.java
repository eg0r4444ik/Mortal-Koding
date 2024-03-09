package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.ConfirmRequest;

import java.util.Optional;

@Repository
public interface ConfirmRepository extends JpaRepository<ConfirmRequest, Long> {
    Optional<ConfirmRequest> findByEmail(String email);
    void deleteByEmail(String email);
    boolean existsByEmail(String email);
}
