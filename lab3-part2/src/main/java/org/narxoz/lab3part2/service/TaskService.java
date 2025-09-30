package org.narxoz.lab3part2.service;

import org.narxoz.lab3part2.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Task save(Task task);
    void delete(Long id);
}
