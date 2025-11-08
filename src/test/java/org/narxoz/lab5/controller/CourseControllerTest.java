package org.narxoz.lab5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /api/v1/courses returns HTTP response with status 200 OK and body List<GetCoursesResponseDto>")
    void getAllCourses_ReturnsValidResponseEntity() throws Exception {
        var course1 = new Courses(UUID.randomUUID(), "Spring boot", "Spring boot courses", 7000L);
        var course2 = new Courses(UUID.randomUUID(), "Java", "Java core", 5000L);
        var courses = List.of(course1, course2);

        var dto1 = new GetCoursesResponseDto(course1.getId(), course1.getName(), course1.getDescription(), course1.getPrice());
        var dto2 = new GetCoursesResponseDto(course2.getId(), course2.getName(), course2.getDescription(), course2.getPrice());

        when(courseService.getCourses()).thenReturn(courses);
        when(courseMapper.toGetCoursesResponseDto(course1)).thenReturn(dto1);
        when(courseMapper.toGetCoursesResponseDto(course2)).thenReturn(dto2);


        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Spring boot"))
                .andExpect(jsonPath("$[1].name").value("Java"))
                .andExpect(jsonPath("$[0].price").value(7000))
                .andExpect(jsonPath("$[1].price").value(5000));

        verify(courseService, times(1)).getCourses();
        verify(courseMapper, times(1)).toGetCoursesResponseDto(course1);
        verify(courseMapper, times(1)).toGetCoursesResponseDto(course2);
    }

    @Test
    void createCourse_ShouldReturnCreatedResponse() throws Exception {
        var requestDto = new CreateCourseRequestDto("Spring Boot", "Backend development", 7000L);
        var course = new Courses(UUID.randomUUID(), requestDto.getName(), requestDto.getDescription(), requestDto.getPrice());
        var responseDto = new CreateCourseResponseDto(course.getId(), course.getName(), course.getDescription(), course.getPrice());
        var courseJson = objectMapper.writeValueAsString(requestDto);

        when(courseMapper.fromCreateCourseRequestDto(requestDto)).thenReturn(course);
        when(courseService.createCourse(course)).thenReturn(course);
        when(courseMapper.toCreateCourseResponseDto(course)).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Spring Boot"))
                .andExpect(jsonPath("$.description").value("Backend development"))
                .andExpect(jsonPath("$.price").value(7000));

        verify(courseService, times(1)).createCourse(course);
        verify(courseMapper, times(1)).toCreateCourseResponseDto(course);
        verify(courseMapper, times(1)).fromCreateCourseRequestDto(requestDto);
    }
}
