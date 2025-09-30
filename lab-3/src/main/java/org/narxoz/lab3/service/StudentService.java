package org.narxoz.lab3.service;

import org.narxoz.lab3.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public List<Student> findAll();
    public Student save(Student s);
    public void delete(Long id);
    public Optional<Student> findById(Long id);
}
