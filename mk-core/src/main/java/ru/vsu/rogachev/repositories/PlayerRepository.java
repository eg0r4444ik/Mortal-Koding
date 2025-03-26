package ru.vsu.rogachev.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.Player;

import java.util.Optional;

@Repository
public interface PlayerRepository  extends JpaRepository<Player, Long> {

    @NotNull Optional<Player> findByHandle(@NotNull String handle);

    void deleteByHandle(@NotNull String handle);

}
