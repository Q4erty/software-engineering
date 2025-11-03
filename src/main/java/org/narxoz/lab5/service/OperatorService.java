package org.narxoz.lab5.service;

import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Operators;

import java.util.List;
import java.util.UUID;

public interface OperatorService {
    List<Operators> getAllOperators();
    Operators createOperator(Operators operator);
    ApplicationRequest assignOperator(UUID id, UUID requestID);
}
