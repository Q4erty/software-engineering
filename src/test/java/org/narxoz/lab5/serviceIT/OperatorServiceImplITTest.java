package org.narxoz.lab5.serviceIT;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Operators;
import org.narxoz.lab5.repository.ApplicationRequestRepository;
import org.narxoz.lab5.repository.OperatorsRepository;
import org.narxoz.lab5.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OperatorServiceImplITTest {

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private OperatorsRepository operatorsRepository;

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    private Operators testOperator;
    private ApplicationRequest testRequest;

    @BeforeEach
    void setup() {
        applicationRequestRepository.deleteAll();
        operatorsRepository.deleteAll();

        Operators op = new Operators();
        op.setName("Operator A");
        op.setSurname("Surname");
        op.setDepartment("IT");
        testOperator = operatorService.createOperator(op);

        ApplicationRequest req = new ApplicationRequest();
        req.setUsername("Alex");
        req.setPhone("87070000000");
        req.setCommentary("Some comment");
        req.setHandled(false);

        testRequest = applicationRequestRepository.save(req);
    }

    @Test
    void getAllOperatorsTest() {
        List<Operators> list = operatorService.getAllOperators();

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());

        for (Operators o : list) {
            Assertions.assertNotNull(o.getId());
            Assertions.assertNotNull(o.getName());
        }
    }

    @Test
    void createOperatorTest() {
        Assertions.assertNotNull(testOperator);
        Assertions.assertNotNull(testOperator.getId());
        Assertions.assertEquals("Operator A", testOperator.getName());
    }

    @Test
    void assignOperatorTest() {
        UUID opId = testOperator.getId();
        UUID reqId = testRequest.getId();

        ApplicationRequest updated = operatorService.assignOperator(opId, reqId);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(reqId, updated.getId());
        Assertions.assertFalse(updated.getOperators().isEmpty());
        Assertions.assertEquals(opId, updated.getOperators().getFirst().getId());

        ApplicationRequest check = applicationRequestRepository.findById(reqId).orElseThrow();
        Assertions.assertEquals(1, check.getOperators().size());
        Assertions.assertEquals(opId, check.getOperators().getFirst().getId());
    }

    @Test
    void assignOperatorNotFoundTest() {
        UUID randomId = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                operatorService.assignOperator(randomId, testRequest.getId())
        );

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                operatorService.assignOperator(testOperator.getId(), randomId)
        );
    }
}
