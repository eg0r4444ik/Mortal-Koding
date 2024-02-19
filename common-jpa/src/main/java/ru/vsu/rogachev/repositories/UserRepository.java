package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.rogachev.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByTelegramUserId(Long id);
}
