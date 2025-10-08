package com.example.lab4.repository;

import com.example.lab4.entity.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {
    List<ApplicationRequest> findByHandled(boolean handled);
}
