package org.narxoz.lab5.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.narxoz.lab5.domain.dto.*;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.domain.entity.Courses;
import org.narxoz.lab5.domain.entity.Operators;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationRequestMapper {

    @Mapping(target = "course", source = "course")
    ApplicationRequest fromApplicationRequest(CreateApplicationRequestDto createApplicationRequestDto);

    CreateApplicationRequestResponseDto toCreateApplicationRequestResponseDto(ApplicationRequest applicationRequest);

    CreateApplicationRequestCourseResponseDto toCreateApplicationRequestCourseResponseDto(Courses courses);


    ApplicationRequest fromUpdateApplicationRequestDto(UpdateApplicationRequestDto updateApplicationRequestDto);

    UpdateApplicationRequestResponseDto toUpdateApplicationRequestResponseDto(ApplicationRequest applicationRequest);

    UpdateApplicationRequestCourseResponseDto toUpdateApplicationRequestCourseResponseDto(Courses courses);


    GetApplicationRequestCourseResponseDto toGetApplicationRequestCourseResponseDto(Courses course);

    GetApplicationRequestOperatorsResponseDto toGetApplicationRequestOperatorsResponseDto(Operators operators);

    GetApplicationRequestResponseDto toGetApplicationRequestResponseDto(ApplicationRequest applicationRequest);


    default Courses map(UUID courseId) {
        if (courseId == null) return null;
        Courses course = new Courses();
        course.setId(courseId);
        return course;
    }
}
