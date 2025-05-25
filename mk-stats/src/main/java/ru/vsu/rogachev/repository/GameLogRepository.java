package ru.vsu.rogachev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.GameLog;

import java.util.List;

@Repository
public interface GameLogRepository extends JpaRepository<GameLog, Long> {

    List<GameLog> findAllByPlayersHandle(String handle);

}
