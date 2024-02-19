package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.User;

import java.util.Optional;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    Optional<GameSession> findByFirstUserId(Long id);
    Optional<GameSession> findBySecondUserId(Long id);
    void deleteByFirstUserId(Long id);
    void deleteBySecondUserId(Long id);
}
