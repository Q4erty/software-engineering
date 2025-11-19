package org.narxoz.lab5.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class    UpdateApplicationRequestDto {

    @NotBlank
    private String username;

    private String commentary;

    @Pattern(regexp = "^\\+7\\d{10}$")
    private String phone;
}
