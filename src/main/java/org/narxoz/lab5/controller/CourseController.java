package org.narxoz.lab5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.narxoz.lab5.domain.CacheMode;
import org.narxoz.lab5.domain.dto.CreateCourseRequestDto;
import org.narxoz.lab5.domain.dto.CreateCourseResponseDto;
import org.narxoz.lab5.domain.dto.GetCoursesResponseDto;
import org.narxoz.lab5.mapper.CourseMapper;
import org.narxoz.lab5.service.CourseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@Slf4j
public class CourseController {

    @Qualifier("courseServiceImpl")
    private final CourseService courseNoneCacheService;

    @Qualifier("courseManualCacheServiceImpl")
    private final CourseService courseManualCacheService;

    private final CourseMapper courseMapper;

    public CourseController(
            @Qualifier("courseServiceImpl") CourseService courseNoneCacheService,
            @Qualifier("courseManualCacheServiceImpl") CourseService courseManualCacheService,
            CourseMapper courseMapper
    ) {
        this.courseNoneCacheService = courseNoneCacheService;
        this.courseManualCacheService = courseManualCacheService;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public ResponseEntity<List<GetCoursesResponseDto>> getAllCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseNoneCacheService.getCourses()
                        .stream()
                        .map(courseMapper::toGetCoursesResponseDto)
                        .toList()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCoursesResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseMapper.toGetCoursesResponseDto(courseNoneCacheService.getById(id))
                );
    }

    @PostMapping
    public ResponseEntity<CreateCourseResponseDto> createCourse(
            @RequestBody @Valid CreateCourseRequestDto courseRequestDto,
            @RequestParam(value = "cacheMode", defaultValue = "NONE") CacheMode cacheMode
    ) {
        log.info("Creating course with cache mode: {}", cacheMode);

        CourseService courseService = resolveService(cacheMode);

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
        courseNoneCacheService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    private CourseService resolveService(CacheMode cacheMode) {
        return switch (cacheMode) {
            case NONE -> courseNoneCacheService;
            case MANUAL -> courseManualCacheService;
        };
    }
}
