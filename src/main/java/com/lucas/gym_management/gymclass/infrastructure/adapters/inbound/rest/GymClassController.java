package com.lucas.gym_management.gymclass.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassOutput;
import com.lucas.gym_management.gymclass.infrastructure.service.GymClassApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/gym-classes")
public class GymClassController {

    private final GymClassApplicationService gymClassApplicationService;

    @PostMapping
    public ResponseEntity<CreateGymClassOutput> create(@Valid @RequestBody CreateGymClassInput input){
        var output = gymClassApplicationService.createGymClass(input);
        URI uri = URI.create("/api/gym-classes/%s".formatted(output.id()));

        return ResponseEntity.created(uri).body(output);
    }
}