package org.narxoz.lab5.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.narxoz.lab5.domain.dto.CreateCourseRequestDto;
import org.narxoz.lab5.domain.dto.CreateCourseResponseDto;
import org.narxoz.lab5.domain.dto.GetCoursesResponseDto;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.mapper.CourseMapper;
import org.narxoz.lab5.service.CourseService;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseController courseController;

    @Test
    @DisplayName("GET /api/v1/courses returns HTTP response with status 200 OK and body List<GetCoursesResponseDto>")
    void getAllCourses_ReturnsValidResponseEntity() {
        var course1 = new Courses(UUID.randomUUID(), "Spring boot", "Spring boot courses", 7000L);
        var course2 = new Courses(UUID.randomUUID(), "Java", "Java core", 5000L);
        var courses = List.of(course1, course2);

        var dto1 = new GetCoursesResponseDto(course1.getId(), course1.getName(), course1.getDescription(), course1.getPrice());
        var dto2 = new GetCoursesResponseDto(course2.getId(), course2.getName(), course2.getDescription(), course2.getPrice());
        var expectedDtos = List.of(dto1, dto2);

        doReturn(courses).when(courseService).getCourses();
        doReturn(dto1).when(courseMapper).toGetCoursesResponseDto(course1);
        doReturn(dto2).when(courseMapper).toGetCoursesResponseDto(course2);

        var responseEntity = courseController.getAllCourses();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDtos, responseEntity.getBody());
    }

    @Test
    void createCourse_ShouldReturnCreatedResponse() {
        var requestDto = new CreateCourseRequestDto("Spring Boot", "Backend development", 7000L);
        var course = new Courses(UUID.randomUUID(), "Spring Boot", "Backend development", 7000L);
        var savedCourse = new Courses(course.getId(), "Spring Boot", "Backend development", 7000L);
        var responseDto = new CreateCourseResponseDto(savedCourse.getId(), "Spring Boot", "Backend development", 7000L);

        doReturn(course).when(courseMapper).fromCreateCourseRequestDto(requestDto);
        doReturn(savedCourse).when(courseService).createCourse(course);
        doReturn(responseDto).when(courseMapper).toCreateCourseResponseDto(savedCourse);

        var response = courseController.createCourse(requestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());

        verify(this.courseService).createCourse(course);

        verifyNoMoreInteractions(this.courseService);
    }


}
