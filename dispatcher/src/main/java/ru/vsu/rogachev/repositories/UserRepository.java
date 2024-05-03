package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelegramId(Long id);
    Optional<User> findByCodeforcesUsername(String username);
    boolean existsByTelegramId(Long id);
    boolean existsByCodeforcesUsername(String username);
    void deleteByTelegramId(Long id);
    void deleteByCodeforcesUsername(String username);
}
