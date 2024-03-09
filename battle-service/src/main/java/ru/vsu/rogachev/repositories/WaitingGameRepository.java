package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.WaitingGame;

@Repository
public interface WaitingGameRepository  extends JpaRepository<WaitingGame, Long> {
}
