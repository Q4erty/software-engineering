package org.narxoz.lab3.service.impl;

import lombok.RequiredArgsConstructor;
import org.narxoz.lab3.entity.Student;
import org.narxoz.lab3.repository.StudentRepository;
import org.narxoz.lab3.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repo;

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Student save(Student s) {
        return repo.save(s);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Optional<Student> findById(Long id) {
        return repo.findById(id);
    }

}
