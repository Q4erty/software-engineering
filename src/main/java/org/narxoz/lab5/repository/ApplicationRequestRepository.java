package org.narxoz.lab5.repository;

import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, UUID> {

}
