package org.narxoz.lab5.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateApplicationRequestDto {

    @NotBlank
    private String username;

    @Size(max = 500)
    private String commentary;

    @Pattern(regexp = "^\\+7\\d{10}$")
    private String phone;

    private UUID course;
}
