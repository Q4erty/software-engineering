package org.narxoz.lab5.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.repository.CoursesRepository;
import org.narxoz.lab5.service.CourseService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseManualCacheServiceImpl implements CourseService {

    private final CoursesRepository courseRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    private static final String CACHE_KEY_PREFIX = "course:";

    @Override
    @SneakyThrows
    public Courses getById(UUID id) {
        var cacheKey = CACHE_KEY_PREFIX + id;

        String objFromCache = stringRedisTemplate.opsForValue().get("course:" + id);

        if (objFromCache != null) {
            log.info("Course found in cache: {}", cacheKey);
            return objectMapper.readValue(objFromCache, Courses.class);
        }

        log.info("Course not found in cache: {}", cacheKey);
        Courses courses = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        stringRedisTemplate.opsForValue().set("course:" + id, objectMapper.writeValueAsString(courses));
        log.info("Course cached: {}", cacheKey);

        return courses;
    }

    @Override
    public List<Courses> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    @SneakyThrows
    public Courses createCourse(Courses course) {
        Courses saved = courseRepository.save(course);

        var cacheKey = CACHE_KEY_PREFIX + saved.getId();

        stringRedisTemplate.opsForValue().setIfAbsent(cacheKey, objectMapper.writeValueAsString(saved));
        return saved;
    }

    @Override
    public void deleteCourse(UUID id) {
        courseRepository.deleteById(id);
    }
}
