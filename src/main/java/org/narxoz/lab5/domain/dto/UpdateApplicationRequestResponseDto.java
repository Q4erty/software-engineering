package org.narxoz.lab5.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateApplicationRequestResponseDto {
    private UUID id;
    private String username;
    private String commentary;
    private String phone;
    private boolean handled;
    private UpdateApplicationRequestCourseResponseDto course;
}
