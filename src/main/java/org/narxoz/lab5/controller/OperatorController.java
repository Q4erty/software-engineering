package org.narxoz.lab5.controller;

import lombok.RequiredArgsConstructor;
import org.narxoz.lab5.domain.dto.*;
import org.narxoz.lab5.mapper.ApplicationRequestMapper;
import org.narxoz.lab5.mapper.OperatorMapper;
import org.narxoz.lab5.service.OperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/operators")
@RequiredArgsConstructor
public class OperatorController {
    private final OperatorService operatorService;
    private final OperatorMapper operatorMapper;
    private final ApplicationRequestMapper applicationRequestMapper;

    @GetMapping
    public ResponseEntity<List<GetOperatorsResponseDto>> getOperators() {
        return ResponseEntity.status(HttpStatus.OK).body(operatorService.getAllOperators().stream().map(operatorMapper::toGetOperatorsResponseDto).toList());
    }

    @PostMapping
    public ResponseEntity<CreateOperatorsResponseDto> createOperator(@RequestBody CreateOperatorsRequestDto operatorsRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operatorMapper.toCreateOperatorsResponseDto(operatorService.createOperator(operatorMapper.frommCreateOperatorsRequestDto(operatorsRequestDto))));
    }

    @PatchMapping("/{id}/assign/{requestId}")
    public ResponseEntity<UpdateApplicationRequestResponseDto> assignOperator(@PathVariable UUID id, @PathVariable UUID requestId) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationRequestMapper.toUpdateApplicationRequestResponseDto(operatorService.assignOperator(id, requestId)));
    }

}
