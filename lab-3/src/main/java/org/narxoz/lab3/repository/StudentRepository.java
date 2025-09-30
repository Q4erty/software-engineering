package org.narxoz.lab3.repository;

import org.narxoz.lab3.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}
