package org.narxoz.lab5.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.narxoz.lab5.domain.dto.CreateOperatorsRequestDto;
import org.narxoz.lab5.domain.dto.CreateOperatorsResponseDto;
import org.narxoz.lab5.domain.dto.GetOperatorsResponseDto;
import org.narxoz.lab5.domain.entity.Operators;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OperatorMapperTest {
    private final OperatorMapper mapper = Mappers.getMapper(OperatorMapper.class);

    private UUID uuid;

    @BeforeEach
    void setUpOperator(){
        UUID uuid = UUID.randomUUID();
    }

    @Test
    void testToGetOperatorsResponseDto() {
        Operators operators = new Operators();
        operators.setId(uuid);
        operators.setName("John");
        operators.setSurname("Nolan");
        operators.setDepartment("IT");
        operators.setRequests(null);

        GetOperatorsResponseDto dto = mapper.toGetOperatorsResponseDto(operators);

        assertNotNull(dto);
        assertEquals(uuid, dto.getId());
        assertEquals("John", dto.getName());
        assertEquals("Nolan", dto.getSurname());
        assertEquals("IT", dto.getDepartment());
    }

    @Test
    void testToCreateOperatorsResponseDto() {
        Operators operators = new Operators();
        operators.setId(uuid);
        operators.setName("John");
        operators.setSurname("Nolan");
        operators.setDepartment("IT");
        operators.setRequests(null);

        CreateOperatorsResponseDto dto = mapper.toCreateOperatorsResponseDto(operators);

        assertNotNull(dto);
        assertEquals(uuid, dto.getId());
        assertEquals("John", dto.getName());
        assertEquals("Nolan", dto.getSurname());
        assertEquals("IT", dto.getDepartment());
    }

    @Test
    void testFromCreateOperatorsRequestDto() {
        CreateOperatorsRequestDto request = new CreateOperatorsRequestDto();
        request.setName("John");
        request.setSurname("Nolan");
        request.setDepartment("IT");

        Operators operators = mapper.frommCreateOperatorsRequestDto(request);

        assertNotNull(operators);
        assertEquals("John", operators.getName());
        assertEquals("Nolan", operators.getSurname());
        assertEquals("IT", operators.getDepartment());
    }

}