package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.Game;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g JOIN FETCH g.players p WHERE p.handle = :handle")
    Optional<Game> findByPlayerHandle(@Param("handle") String handle);

}
