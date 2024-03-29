package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
