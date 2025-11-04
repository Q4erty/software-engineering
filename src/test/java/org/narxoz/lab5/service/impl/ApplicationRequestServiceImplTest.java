package org.narxoz.lab5.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.repository.ApplicationRequestRepository;
import org.narxoz.lab5.repository.CoursesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationRequestServiceImplTest {

    @Mock
    private ApplicationRequestRepository applicationRequestRepository;

    @Mock
    private CoursesRepository coursesRepository;

    @InjectMocks
    private ApplicationRequestServiceImpl applicationRequestService;

    private ApplicationRequest request;
    private Courses course;

    @BeforeEach
    void setUp() {
        course = new Courses(UUID.randomUUID(), "Spring Boot", "Learn Spring Boot", 7000L);
        request = new ApplicationRequest();
        request.setId(UUID.randomUUID());
        request.setUsername("Jhon");
        request.setPhone("87070000000");
        request.setCommentary("Test commentary");
    }

    @Test
    void testGetAllApplicationRequest() {
        List<ApplicationRequest> list = List.of(request);
        doReturn(list).when(applicationRequestRepository).findAll();

        List<ApplicationRequest> result = applicationRequestService.getAllApplicationRequest();

        assertEquals(1, result.size());
        assertEquals("Jhon", result.getFirst().getUsername());
        verify(applicationRequestRepository, times(1)).findAll();
    }

    @Test
    void testGetApplicationRequestById() {
        doReturn(Optional.of(request)).when(applicationRequestRepository).findById(request.getId());

        ApplicationRequest result = applicationRequestService.getApplicationRequestById(request.getId());

        assertNotNull(result);
        assertEquals(request.getId(), result.getId());
        verify(applicationRequestRepository, times(1)).findById(request.getId());
    }

    @Test
    void testCreateApplicationRequest() {
        UUID courseId = course.getId();

        doReturn(Optional.of(course)).when(coursesRepository).findById(courseId);
        doReturn(request).when(applicationRequestRepository).save(any(ApplicationRequest.class));

        ApplicationRequest result = applicationRequestService.createApplicationRequest(request, courseId);

        assertNotNull(result);
        assertFalse(result.isHandled());
        assertEquals(course, result.getCourse());
        verify(applicationRequestRepository, times(1)).save(request);
    }

    @Test
    void testUpdateApplicationRequest() {
        doReturn(Optional.of(request)).when(applicationRequestRepository).findById(request.getId());
        doReturn(request).when(applicationRequestRepository).save(any(ApplicationRequest.class));

        ApplicationRequest updated = new ApplicationRequest();
        updated.setUsername("UpdatedUser");
        updated.setPhone("87771111111");
        updated.setCommentary("Updated comment");

        ApplicationRequest result = applicationRequestService.updateApplicationRequest(updated, request.getId());

        assertNotNull(result);
        verify(applicationRequestRepository, times(1)).save(any(ApplicationRequest.class));
    }

    @Test
    void testDeleteApplicationRequest() {
        UUID id = UUID.randomUUID();

        applicationRequestService.deleteApplicationRequest(id);

        verify(applicationRequestRepository, times(1)).deleteById(id);
    }
}
