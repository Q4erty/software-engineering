package org.narxoz.lab5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.narxoz.lab5.domain.dto.*;
import org.narxoz.lab5.domain.entity.ApplicationRequest;
import org.narxoz.lab5.mapper.ApplicationRequestMapper;
import org.narxoz.lab5.service.ApplicationRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class ApplicationRequestController {

    private final ApplicationRequestService applicationRequestService;
    private final ApplicationRequestMapper applicationRequestMapper;

    @GetMapping
    public ResponseEntity<List<GetApplicationRequestResponseDto>> getAllApplicationRequest() {
        return ResponseEntity.ok(applicationRequestService.getAllApplicationRequest()
                .stream().map(applicationRequestMapper::toGetApplicationRequestResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetApplicationRequestResponseDto> getApplicationRequest(@PathVariable UUID id) {
        ApplicationRequest applicationRequest = applicationRequestService.getApplicationRequestById(id);
        return ResponseEntity.ok(applicationRequestMapper.toGetApplicationRequestResponseDto(applicationRequest));
    }

    @PostMapping
    public ResponseEntity<CreateApplicationRequestResponseDto> createApplicationRequest(@RequestBody @Valid CreateApplicationRequestDto applicationRequestDto){
        ApplicationRequest applicationRequest = applicationRequestMapper.fromApplicationRequest(applicationRequestDto);
        ApplicationRequest applicationRequestCreated = applicationRequestService.createApplicationRequest(applicationRequest, applicationRequestDto.getCourse());
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationRequestMapper.toCreateApplicationRequestResponseDto(applicationRequestCreated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateApplicationRequestResponseDto> updateApplicationRequest(
            @RequestBody UpdateApplicationRequestDto applicationRequestDto,
            @PathVariable UUID id
            ){
        ApplicationRequest applicationRequest = applicationRequestMapper.fromUpdateApplicationRequestDto(applicationRequestDto);
        ApplicationRequest applicationRequestUpdated = applicationRequestService.updateApplicationRequest(applicationRequest, id);
        return ResponseEntity.status(HttpStatus.OK).body(applicationRequestMapper.toUpdateApplicationRequestResponseDto(applicationRequestUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationRequest(@PathVariable UUID id) {
        applicationRequestService.deleteApplicationRequest(id);
        return ResponseEntity.noContent().build();
    }
}
