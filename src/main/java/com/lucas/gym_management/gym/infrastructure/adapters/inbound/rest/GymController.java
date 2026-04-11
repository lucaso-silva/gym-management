package com.lucas.gym_management.gym.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/gyms")
public class GymController {

    private final CreateGymUseCase createGymUseCase;

    @PostMapping
    public ResponseEntity<CreateGymOutput> create(@Valid @RequestBody CreateGymInput input) {
        var output = createGymUseCase.createGym(input);
        URI uri = URI.create("/gyms/%s".formatted(output.id()));

        return ResponseEntity.created(uri).body(output);
    }
}
