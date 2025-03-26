package ru.vsu.rogachev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.rogachev.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
