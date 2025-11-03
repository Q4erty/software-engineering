package org.narxoz.lab5.service;

import org.narxoz.lab5.domain.entity.Courses;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<Courses> getCourses();
    Courses createCourse(Courses course);
    void deleteCourse(UUID id);
}
