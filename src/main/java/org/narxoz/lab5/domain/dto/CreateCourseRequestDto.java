package org.narxoz.lab5.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCourseRequestDto {
    private String name;
    private String description;
    private Long price;
}
