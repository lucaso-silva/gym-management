package com.lucas.gym_management.gym.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymOutput;
import com.lucas.gym_management.gym.infrastructure.service.GymApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/gyms")
public class GymController {

    private final GymApplicationService gymApplicationService;

    @PostMapping
    public ResponseEntity<CreateGymOutput> create(@Valid @RequestBody CreateGymInput input) {
        var output = gymApplicationService.createGym(input);
        URI uri = URI.create("/api/gyms/%s".formatted(output.id()));

        return ResponseEntity.created(uri).body(output);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymOutput> getGymById(@PathVariable UUID id) {
        var gymById = gymApplicationService.getGymById(id);

        return ResponseEntity.ok(gymById);
    }

    @GetMapping
    public ResponseEntity<List<ListGymOutput>> getAllGyms() {
        var output = gymApplicationService.listGyms();

        return ResponseEntity.ok(output);
    }
}
