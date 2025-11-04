package org.narxoz.lab5.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Operators;
import org.narxoz.lab5.repository.ApplicationRequestRepository;
import org.narxoz.lab5.repository.OperatorsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperatorServiceImplTest {

    @Mock
    private OperatorsRepository operatorsRepository;

    @Mock
    private ApplicationRequestRepository applicationRequestRepository;

    @InjectMocks
    private OperatorServiceImpl operatorService;

    private Operators operator;
    private ApplicationRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        operator = new Operators();
        operator.setId(UUID.randomUUID());

        request = new ApplicationRequest();
        request.setId(UUID.randomUUID());
        request.setOperators(new ArrayList<>());
    }

    @Test
    void testGetAllOperators() {
        List<Operators> expected = List.of(operator);
        doReturn(expected).when(operatorsRepository).findAll();

        List<Operators> result = operatorService.getAllOperators();

        assertEquals(1, result.size());
        assertEquals(operator, result.get(0));
        verify(operatorsRepository, times(1)).findAll();
    }

    @Test
    void testCreateOperator() {
        doReturn(operator).when(operatorsRepository).save(operator);

        Operators result = operatorService.createOperator(operator);

        assertNotNull(result);
        assertEquals(operator, result);
        verify(operatorsRepository, times(1)).save(operator);
    }

    @Test
    void testAssignOperator() {
        UUID opId = operator.getId();
        UUID reqId = request.getId();

        doReturn(Optional.of(operator)).when(operatorsRepository).findById(opId);
        doReturn(Optional.of(request)).when(applicationRequestRepository).findById(reqId);
        doReturn(request).when(applicationRequestRepository).save(request);

        ApplicationRequest result = operatorService.assignOperator(opId, reqId);

        assertTrue(result.getOperators().contains(operator));
        verify(operatorsRepository, times(1)).findById(opId);
        verify(applicationRequestRepository, times(1)).save(request);
    }
}
