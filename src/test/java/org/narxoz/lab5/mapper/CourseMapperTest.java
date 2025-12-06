package org.narxoz.lab5.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.narxoz.lab5.domain.dto.CreateCourseRequestDto;
import org.narxoz.lab5.domain.dto.CreateCourseResponseDto;
import org.narxoz.lab5.domain.dto.GetCoursesResponseDto;
import org.narxoz.lab5.domain.entity.Courses;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CourseMapperTest {

    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    private UUID uuid;
    private Courses course;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();

        course = new Courses();
        course.setId(uuid);
        course.setName("Java");
        course.setDescription("Java Core");
        course.setPrice(5000L);
    }

    @Test
    void testToGetCoursesResponseDto() {
        GetCoursesResponseDto dto = mapper.toGetCoursesResponseDto(course);

        assertNotNull(dto);
        assertEquals(uuid, dto.getId());
        assertEquals("Java", dto.getName());
        assertEquals("Java Core", dto.getDescription());
        assertEquals(5000L, dto.getPrice());
    }

    @Test
    void testToCreateCourseResponseDto() {
        CreateCourseResponseDto dto = mapper.toCreateCourseResponseDto(course);

        assertNotNull(dto);
        assertEquals(uuid, dto.getId());
        assertEquals("Java", dto.getName());
        assertEquals("Java Core", dto.getDescription());
        assertEquals(5000L, dto.getPrice());
    }

    @Test
    void testFromCreateCourseRequestDto() {
        CreateCourseRequestDto request = new CreateCourseRequestDto();
        request.setName("Spring Boot");
        request.setDescription("Spring Boot");
        request.setPrice(5000L);

        Courses newCourse = mapper.fromCreateCourseRequestDto(request);

        assertNotNull(newCourse);
        assertEquals("Spring Boot", newCourse.getName());
        assertEquals("Spring Boot", newCourse.getDescription());
        assertEquals(5000L, newCourse.getPrice());
    }
}
