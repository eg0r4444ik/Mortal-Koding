package ru.vsu.rogachev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.GameParameters;

@Repository
public interface GameParametersRepository extends JpaRepository<GameParameters, Long> {
}
