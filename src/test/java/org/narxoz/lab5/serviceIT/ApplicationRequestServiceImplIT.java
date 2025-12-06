package org.narxoz.lab5.serviceIT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.repository.ApplicationRequestRepository;
import org.narxoz.lab5.repository.CoursesRepository;
import org.narxoz.lab5.service.ApplicationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationRequestServiceImplIT {

    @Autowired
    private ApplicationRequestService applicationRequestService;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    private ApplicationRequest testRequest;
    private Courses testCourse;

    @BeforeEach
    void setup() {

        applicationRequestRepository.deleteAll();
        coursesRepository.deleteAll();

        Courses c = new Courses();
        c.setName("Java Backend");
        c.setDescription("java");
        c.setPrice(5000L);
        testCourse = coursesRepository.save(c);

        ApplicationRequest request = new ApplicationRequest();
        request.setUsername("Alex");
        request.setCommentary("Hello");
        request.setPhone("87077001122");

        testRequest = applicationRequestService.createApplicationRequest(request, testCourse.getId());
    }

    @Test
    void createApplicationRequestTest() {
        Assertions.assertNotNull(testRequest);
        Assertions.assertNotNull(testRequest.getId());
        Assertions.assertEquals("Alex", testRequest.getUsername());
        Assertions.assertEquals("Hello", testRequest.getCommentary());
        Assertions.assertEquals("87077001122", testRequest.getPhone());
        Assertions.assertFalse(testRequest.isHandled());
        Assertions.assertEquals(testCourse.getId(), testRequest.getCourse().getId());
    }

    @Test
    void getAllApplicationRequestTest() {
        List<ApplicationRequest> list = applicationRequestService.getAllApplicationRequest();
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());

        for (ApplicationRequest r : list) {
            Assertions.assertNotNull(r.getId());
            Assertions.assertNotNull(r.getUsername());
            Assertions.assertNotNull(r.getPhone());
        }
    }

    @Test
    void getApplicationRequestByIdTest() {
        UUID id = testRequest.getId();

        ApplicationRequest found = applicationRequestService.getApplicationRequestById(id);
        Assertions.assertNotNull(found);
        Assertions.assertEquals(id, found.getId());

        Assertions.assertThrows(Exception.class, () ->
                applicationRequestService.getApplicationRequestById(UUID.randomUUID())
        );
    }

    @Test
    void updateApplicationRequestTest() {
        UUID id = testRequest.getId();

        ApplicationRequest update = new ApplicationRequest();
        update.setUsername("Updated User");
        update.setCommentary("Updated Comment");
        update.setPhone("999999");

        ApplicationRequest updated = applicationRequestService.updateApplicationRequest(update, id);

        Assertions.assertEquals("Updated User", updated.getUsername());
        Assertions.assertEquals("Updated Comment", updated.getCommentary());
        Assertions.assertEquals("999999", updated.getPhone());

        ApplicationRequest check = applicationRequestService.getApplicationRequestById(id);
        Assertions.assertEquals("Updated User", check.getUsername());
        Assertions.assertEquals("Updated Comment", check.getCommentary());
        Assertions.assertEquals("999999", check.getPhone());
    }

    @Test
    void deleteApplicationRequestTest() {
        UUID id = testRequest.getId();

        applicationRequestService.deleteApplicationRequest(id);

        Assertions.assertThrows(Exception.class, () ->
                applicationRequestService.getApplicationRequestById(id)
        );
    }
}
