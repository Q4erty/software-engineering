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
        // given
        var course1 = new Courses(UUID.randomUUID(), "Java", "Java core", 5000L);
        var course2 = new Courses(UUID.randomUUID(), "Spring Boot", "Spring backend", 7000L);
        var list = List.of(course1, course2);

        doReturn(list).when(courseRepository).findAll();

        // when
        var result = courseService.getCourses();

        // then
        assertEquals(2, result.size());
        assertEquals("Java", result.getFirst().getName());
        verify(courseRepository).findAll();
    }

    @Test
    void createCourse_ShouldSaveAndReturnCourse() {
        // given
        var course = new Courses(UUID.randomUUID(), "Spring Boot", "Awesome course", 7000L);

        doReturn(course).when(courseRepository).save(course);

        // when
        var result = courseService.createCourse(course);

        // then
        assertNotNull(result);
        assertEquals("Spring Boot", result.getName());
        verify(courseRepository).save(course);
    }

    @Test
    void deleteCourse_ShouldCallRepository() {
        // given
        var id = UUID.randomUUID();

        // when        // ✅ doReturn вместо when

        courseService.deleteCourse(id);

        // then
        verify(courseRepository).deleteById(id);
    }
}
