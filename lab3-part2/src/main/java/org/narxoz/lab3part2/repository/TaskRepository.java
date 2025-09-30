package org.narxoz.lab3part2.repository;

import org.narxoz.lab3part2.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
