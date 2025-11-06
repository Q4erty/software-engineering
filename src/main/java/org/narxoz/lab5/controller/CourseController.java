package org.narxoz.lab5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.narxoz.lab5.domain.dto.CreateCourseRequestDto;
import org.narxoz.lab5.domain.dto.CreateCourseResponseDto;
import org.narxoz.lab5.domain.dto.GetCoursesResponseDto;
import org.narxoz.lab5.mapper.CourseMapper;
import org.narxoz.lab5.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @GetMapping
    public ResponseEntity<List<GetCoursesResponseDto>> getAllCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseService.getCourses()
                        .stream()
                        .map(courseMapper::toGetCoursesResponseDto)
                        .toList()
                );
    }

    @PostMapping
    public ResponseEntity<CreateCourseResponseDto> createCourse(@RequestBody @Valid CreateCourseRequestDto courseRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseMapper.toCreateCourseResponseDto(
                                courseService.createCourse(
                                        courseMapper.fromCreateCourseRequestDto(courseRequestDto)
                                )
                        )
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
