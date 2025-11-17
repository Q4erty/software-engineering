package org.narxoz.lab5.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.repository.CoursesRepository;
import org.narxoz.lab5.service.CourseService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseManualCacheServiceImpl implements CourseService {

    private final CoursesRepository courseRepository;
    private final RedisTemplate<String, Courses> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "course:";
    private static final long CACHE_TTL_MINUTES = 1;

    @Override
    public Courses getById(UUID id) {
        var cacheKey = CACHE_KEY_PREFIX + id;

        Courses course = redisTemplate.opsForValue().get(cacheKey);

        if (course != null) {
            log.info("Course found in cache: {}", cacheKey);
            return course;
        }

        log.info("Course not found in cache: {}", cacheKey);
        Courses courseFromDB = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        redisTemplate.opsForValue().set(cacheKey, courseFromDB, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        log.info("Course cached: {}", cacheKey);

        return courseFromDB;
    }

    @Override
    public List<Courses> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Courses createCourse(Courses course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(UUID id) {
        log.info("Deleting course from DB: {}", id);
        if(!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found, ID: " + id);
        }
        courseRepository.deleteById(id);

        String cacheKey = CACHE_KEY_PREFIX + id;

        courseRepository.deleteById(id);
        redisTemplate.delete(cacheKey);
        log.info("Cache invalidated for deleted course entity with ID:, {}", id);
    }
}
