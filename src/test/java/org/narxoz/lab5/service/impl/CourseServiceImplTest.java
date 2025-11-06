package org.narxoz.lab5.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.repository.CoursesRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CoursesRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void getCourses_ShouldReturnCoursesList() {
        var course1 = new Courses(UUID.randomUUID(), "Java", "Java core", 5000L);
        var course2 = new Courses(UUID.randomUUID(), "Spring Boot", "backend", 7000L);
        var list = List.of(course1, course2);

        doReturn(list).when(courseRepository).findAll();

        var result = courseService.getCourses();

        assertEquals(2, result.size());
        assertEquals("Java", result.getFirst().getName());
        verify(courseRepository).findAll();
    }

    @Test
    void createCourse_ShouldSaveAndReturnCourse() {
        var course = new Courses(UUID.randomUUID(), "Spring Boot", "course", 7000L);

        doReturn(course).when(courseRepository).save(course);

        var result = courseService.createCourse(course);

        assertNotNull(result);
        assertEquals("Spring Boot", result.getName());
        verify(courseRepository).save(course);
    }

    @Test
    void deleteCourse_ShouldCallRepository() {
        var id = UUID.randomUUID();

        courseService.deleteCourse(id);

        verify(courseRepository).deleteById(id);
    }
}
