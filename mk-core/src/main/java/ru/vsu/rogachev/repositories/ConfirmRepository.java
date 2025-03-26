package ru.vsu.rogachev.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.ConfirmRequest;

import java.util.Optional;

@Repository
public interface ConfirmRepository extends JpaRepository<ConfirmRequest, Long> {

    @NotNull Optional<ConfirmRequest> findByEmail(@NotNull String email);

    void deleteByEmail(@NotNull String email);

    boolean existsByEmail(@NotNull String email);

}
