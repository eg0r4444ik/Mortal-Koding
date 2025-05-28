package ru.vsu.rogachev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.Game;
import ru.vsu.rogachev.entity.enums.GameState;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g JOIN FETCH g.players p WHERE p.handle = :handle")
    Optional<Game> findByPlayerHandle(@Param("handle") String handle);

    List<Game> findAllByState(GameState state);

    void deleteAllByState(GameState state);

}
