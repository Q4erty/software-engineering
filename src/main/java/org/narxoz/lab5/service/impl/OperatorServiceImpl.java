package org.narxoz.lab5.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Operators;
import org.narxoz.lab5.repository.ApplicationRequestRepository;
import org.narxoz.lab5.repository.OperatorsRepository;
import org.narxoz.lab5.service.OperatorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {
    private final OperatorsRepository operatorsRepository;
    private final ApplicationRequestRepository applicationRequestRepository;

    @Override
    public List<Operators> getAllOperators() {
        return operatorsRepository.findAll();
    }

    @Override
    public Operators createOperator(Operators operator) {
        return operatorsRepository.save(operator);
    }

    @Override
    public ApplicationRequest assignOperator(UUID id, UUID requestID) {
        Operators operators = operatorsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(requestID).orElseThrow(() -> new EntityNotFoundException("Not found"));

        applicationRequest.getOperators().add(operators);

        return applicationRequestRepository.save(applicationRequest);
    }
}
