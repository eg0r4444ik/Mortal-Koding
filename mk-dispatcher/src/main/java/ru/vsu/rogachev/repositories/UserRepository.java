package ru.vsu.rogachev.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @NotNull Optional<User> findByChatId(@NotNull Long id);

    @NotNull Optional<User> findByCodeforcesUsername(@NotNull String username);

    boolean existsByChatId(@NotNull Long id);

    boolean existsByCodeforcesUsername(@NotNull String username);

    void deleteByChatId(@NotNull Long id);

    void deleteByCodeforcesUsername(@NotNull String username);

}
