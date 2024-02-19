package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByTelegramUserId(Long id);
    Optional<User> findUserByCodeforcesUsername(String username);
    void deleteByTelegramUserId(Long id);
    void deleteByCodeforcesUsername(String username);
}
