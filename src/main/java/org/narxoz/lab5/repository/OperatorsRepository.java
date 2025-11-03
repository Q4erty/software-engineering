package org.narxoz.lab5.repository;

import org.narxoz.lab5.domain.entity.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OperatorsRepository extends JpaRepository<Operators, UUID> {
}
