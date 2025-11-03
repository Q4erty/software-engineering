package org.narxoz.lab5.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateApplicationRequestDto {
    private String username;
    private String commentary;
    private String phone;
}
