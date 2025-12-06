package org.narxoz.lab5.serviceIT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.repository.ApplicationRequestRepository;
import org.narxoz.lab5.repository.CoursesRepository;
import org.narxoz.lab5.repository.OperatorsRepository;
import org.narxoz.lab5.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class CourseServiceImplITTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    private Courses testCourse;

    @BeforeEach
    void setup() {
        applicationRequestRepository.deleteAll();
        applicationRequestRepository.flush();

        coursesRepository.deleteAll();
        coursesRepository.flush();

        Courses c = new Courses();
        c.setName("Java Backend");
        c.setDescription("Spring Boot");
        c.setPrice(10000L);

        testCourse = courseService.createCourse(c);
    }

    @Test
    void getCoursesTest() {
        List<Courses> list = courseService.getCourses();

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());

        for (Courses c : list) {
            Assertions.assertNotNull(c.getId());
            Assertions.assertNotNull(c.getName());
            Assertions.assertNotNull(c.getDescription());
        }
    }

    @Test
    void createCourseTest() {
        Assertions.assertNotNull(testCourse);
        Assertions.assertNotNull(testCourse.getId());
        Assertions.assertEquals("Java Backend", testCourse.getName());
        Assertions.assertEquals("Spring Boot", testCourse.getDescription());
        Assertions.assertEquals(10000L, testCourse.getPrice());
    }

    @Test
    void deleteCourseTest() {
        UUID id = testCourse.getId();

        courseService.deleteCourse(id);

        Assertions.assertFalse(coursesRepository.findById(id).isPresent());
    }
}
