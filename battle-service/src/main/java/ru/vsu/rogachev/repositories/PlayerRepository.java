package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.Player;

@Repository
public interface PlayerRepository  extends JpaRepository<Player, Long> {

    void deleteByHandle(String handle);

    Player getByHandle(String handle);



}
