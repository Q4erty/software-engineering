package org.narxoz.lab5.service;

import lombok.RequiredArgsConstructor;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.repository.CoursesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CoursesRepository courseRepository;

    @Override
    public List<Courses> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Courses createCourse(Courses course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(UUID id) {
        courseRepository.deleteById(id);
    }
}
