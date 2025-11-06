package org.narxoz.lab5.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.repository.ApplicationRequestRepository;
import org.narxoz.lab5.repository.CoursesRepository;
import org.narxoz.lab5.service.ApplicationRequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationRequestServiceImpl implements ApplicationRequestService {

    private final ApplicationRequestRepository applicationRequestRepository;
    private final CoursesRepository coursesRepository;

    @Override
    public List<ApplicationRequest> getAllApplicationRequest() {
        return applicationRequestRepository.findAll();
    }

    @Override
    public ApplicationRequest getApplicationRequestById(UUID id) {
        return applicationRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
    }

    @Override
    public ApplicationRequest createApplicationRequest(ApplicationRequest applicationRequest, UUID courseId) {
        Courses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + courseId));

        applicationRequest.setHandled(false);
        applicationRequest.setCourse(course);

        return applicationRequestRepository.save(applicationRequest);
    }

    @Override
    public ApplicationRequest updateApplicationRequest(ApplicationRequest applicationRequestUpdate, UUID id) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
        applicationRequest.setUsername(applicationRequest.getUsername());
        applicationRequest.setCommentary(applicationRequest.getCommentary());
        applicationRequest.setPhone(applicationRequest.getPhone());

        return applicationRequestRepository.save(applicationRequest);
    }

    @Override
    public void deleteApplicationRequest(UUID id) {
        applicationRequestRepository.deleteById(id);
    }
}
