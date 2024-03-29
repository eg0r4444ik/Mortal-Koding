package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.GameSession;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
}
