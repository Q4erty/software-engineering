package org.narxoz.lab5.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.narxoz.lab5.domain.dto.CreateCourseRequestDto;
import org.narxoz.lab5.domain.dto.CreateCourseResponseDto;
import org.narxoz.lab5.domain.dto.GetCoursesResponseDto;
import org.narxoz.lab5.domain.entity.Courses;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {
    GetCoursesResponseDto toGetCoursesResponseDto(Courses courses);

    CreateCourseResponseDto toCreateCourseResponseDto(Courses courses);

    Courses fromCreateCourseRequestDto(CreateCourseRequestDto createCourseRequestDto);
}
