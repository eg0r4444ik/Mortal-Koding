package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.rogachev.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByTelegramUserId(Long id);
}
