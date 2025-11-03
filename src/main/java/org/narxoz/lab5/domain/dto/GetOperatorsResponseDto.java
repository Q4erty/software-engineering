package org.narxoz.lab5.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetOperatorsResponseDto {
    private UUID id;
    private String name;
    private String surname;
    private String department;
}
