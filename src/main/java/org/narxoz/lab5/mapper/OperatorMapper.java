package org.narxoz.lab5.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.narxoz.lab5.domain.dto.CreateOperatorsRequestDto;
import org.narxoz.lab5.domain.dto.CreateOperatorsResponseDto;
import org.narxoz.lab5.domain.dto.GetOperatorsResponseDto;
import org.narxoz.lab5.domain.entity.Operators;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OperatorMapper {

    GetOperatorsResponseDto toGetOperatorsResponseDto(Operators operators);

    CreateOperatorsResponseDto toCreateOperatorsResponseDto(Operators operators);

    Operators frommCreateOperatorsRequestDto(CreateOperatorsRequestDto createOperatorsRequestDto);
}
