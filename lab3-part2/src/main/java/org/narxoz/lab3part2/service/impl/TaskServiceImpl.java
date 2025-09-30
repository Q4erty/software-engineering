package org.narxoz.lab3part2.service.impl;

import lombok.RequiredArgsConstructor;
import org.narxoz.lab3part2.entity.Task;
import org.narxoz.lab3part2.repository.TaskRepository;
import org.narxoz.lab3part2.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    public Task save(Task task) {
        return repository.save(task);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
