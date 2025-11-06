package org.narxoz.lab5.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOperatorsRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String department;
}
