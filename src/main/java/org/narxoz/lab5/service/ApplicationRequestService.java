package org.narxoz.lab5.service;

import org.narxoz.lab5.domain.entity.ApplicationRequest;

import java.util.List;
import java.util.UUID;

public interface ApplicationRequestService {
    List<ApplicationRequest> getAllApplicationRequest();
    ApplicationRequest getApplicationRequestById(UUID id);
    ApplicationRequest createApplicationRequest(ApplicationRequest applicationRequest, UUID courseId);
    ApplicationRequest updateApplicationRequest(ApplicationRequest applicationRequest, UUID id);
    void deleteApplicationRequest(UUID id);
}
