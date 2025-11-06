package org.narxoz.lab5.domain.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCourseRequestDto {

    @NotBlank
    private String name;

    @Size(max = 500)
    @NotBlank
    private String description;

    @Digits(integer = 8, fraction = 5)
    @Positive
    private Long price;
}
