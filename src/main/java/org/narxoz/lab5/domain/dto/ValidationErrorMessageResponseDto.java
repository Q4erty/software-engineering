package org.narxoz.lab5.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationErrorMessageResponseDto {
    private String field;
    private String rejectedValue;
    private String defaultMessage;
}
