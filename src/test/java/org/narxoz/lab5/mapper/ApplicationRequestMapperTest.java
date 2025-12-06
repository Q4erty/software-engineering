package org.narxoz.lab5.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.narxoz.lab5.domain.dto.*;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.domain.entity.Operators;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationRequestMapperTest {

    private final ApplicationRequestMapper mapper = Mappers.getMapper(ApplicationRequestMapper.class);

    private UUID requestId;
    private UUID courseId;
    private UUID operatorId;

    private Courses course;
    private Operators operator;
    private ApplicationRequest applicationRequest;

    @BeforeEach
    void setUp() {
        requestId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        operatorId = UUID.randomUUID();

        course = new Courses();
        course.setId(courseId);
        course.setName("Java Core");
        course.setDescription("Basic Java course");
        course.setPrice(5000L);

        operator = new Operators();
        operator.setId(operatorId);
        operator.setName("John");
        operator.setSurname("Doe");
        operator.setDepartment("IT");

        applicationRequest = new ApplicationRequest();
        applicationRequest.setId(requestId);
        applicationRequest.setUsername("user1");
        applicationRequest.setCommentary("some comment");
        applicationRequest.setPhone("+77001112233");
        applicationRequest.setHandled(false);
        applicationRequest.setCourse(course);
        applicationRequest.setOperators(List.of(operator));
    }

    @Test
    void testFromApplicationRequestDto() {
        CreateApplicationRequestDto dto = new CreateApplicationRequestDto();
        dto.setUsername("alex");
        dto.setCommentary("test");
        dto.setPhone("+77001112233");
        dto.setCourse(courseId);

        ApplicationRequest mapped = mapper.fromApplicationRequest(dto);

        assertNotNull(mapped);
        assertEquals("alex", mapped.getUsername());
        assertEquals("test", mapped.getCommentary());
        assertEquals("+77001112233", mapped.getPhone());
        assertEquals(courseId, mapped.getCourse().getId());
    }

    @Test
    void testToCreateApplicationRequestResponseDto() {
        CreateApplicationRequestResponseDto dto = mapper.toCreateApplicationRequestResponseDto(applicationRequest);

        assertNotNull(dto);
        assertEquals(requestId, dto.getId());
        assertEquals("user1", dto.getUsername());
        assertEquals("some comment", dto.getCommentary());
        assertEquals("+77001112233", dto.getPhone());
        assertEquals(false, dto.isHandled());

        assertNotNull(dto.getCourse());
        assertEquals(courseId, dto.getCourse().getId());
    }

    @Test
    void testToCreateApplicationRequestCourseResponseDto() {
        CreateApplicationRequestCourseResponseDto dto = mapper.toCreateApplicationRequestCourseResponseDto(course);

        assertNotNull(dto);
        assertEquals(courseId, dto.getId());
        assertEquals("Java Core", dto.getName());
        assertEquals("Basic Java course", dto.getDescription());
        assertEquals(5000L, dto.getPrice());
    }

    @Test
    void testToUpdateApplicationRequestOperatorsResponseDto() {
        UpdateApplicationRequestOperatorsResponseDto dto =
                mapper.toUpdateApplicationRequestOperatorsResponseDto(operator);

        assertNotNull(dto);
        assertEquals(operatorId, dto.getId());
        assertEquals("John", dto.getName());
        assertEquals("Doe", dto.getSurname());
        assertEquals("IT", dto.getDepartment());
    }

    @Test
    void testFromUpdateApplicationRequestDto() {
        UpdateApplicationRequestDto dto = new UpdateApplicationRequestDto();
        dto.setUsername("newUser");
        dto.setCommentary("updated");
        dto.setPhone("+77005556677");

        ApplicationRequest mapped = mapper.fromUpdateApplicationRequestDto(dto);

        assertNotNull(mapped);
        assertEquals("newUser", mapped.getUsername());
        assertEquals("updated", mapped.getCommentary());
        assertEquals("+77005556677", mapped.getPhone());
    }

    @Test
    void testToUpdateApplicationRequestResponseDto() {
        UpdateApplicationRequestResponseDto dto =
                mapper.toUpdateApplicationRequestResponseDto(applicationRequest);

        assertNotNull(dto);
        assertEquals(requestId, dto.getId());
        assertEquals("user1", dto.getUsername());
        assertEquals("some comment", dto.getCommentary());
        assertEquals("+77001112233", dto.getPhone());
        assertFalse(dto.isHandled());

        assertNotNull(dto.getCourse());
        assertEquals(courseId, dto.getCourse().getId());

        assertEquals(1, dto.getOperators().size());
        assertEquals(operatorId, dto.getOperators().get(0).getId());
    }

    @Test
    void testToGetApplicationRequestCourseResponseDto() {
        GetApplicationRequestCourseResponseDto dto =
                mapper.toGetApplicationRequestCourseResponseDto(course);

        assertNotNull(dto);
        assertEquals(courseId, dto.getId());
        assertEquals("Java Core", dto.getName());
    }

    @Test
    void testToGetApplicationRequestOperatorsResponseDto() {
        GetApplicationRequestOperatorsResponseDto dto =
                mapper.toGetApplicationRequestOperatorsResponseDto(operator);

        assertNotNull(dto);
        assertEquals(operatorId, dto.getId());
        assertEquals("John", dto.getName());
        assertEquals("Doe", dto.getSurname());
    }

    @Test
    void testToGetApplicationRequestResponseDto() {
        GetApplicationRequestResponseDto dto =
                mapper.toGetApplicationRequestResponseDto(applicationRequest);

        assertNotNull(dto);
        assertEquals(requestId, dto.getId());
        assertEquals("user1", dto.getUsername());
        assertEquals("some comment", dto.getCommentary());
        assertEquals("+77001112233", dto.getPhone());
        assertFalse(dto.isHandled());
        assertEquals(courseId, dto.getCourse().getId());
        assertEquals(1, dto.getOperators().size());
    }
}
