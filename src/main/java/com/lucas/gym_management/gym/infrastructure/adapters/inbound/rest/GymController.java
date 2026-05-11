package com.lucas.gym_management.gym.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.AddGymClassInput;
import com.lucas.gym_management.gym.application.ports.inbound.update.UpdateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.AddMemberInput;
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

    @GetMapping("/{gymId}")
    public ResponseEntity<GymOutput> getGymById(@PathVariable UUID gymId) {
        var gymById = gymApplicationService.getGymById(gymId);

        return ResponseEntity.ok(gymById);
    }

    @GetMapping
    public ResponseEntity<List<ListGymOutput>> getAllGyms() {
        var output = gymApplicationService.listGyms();

        return ResponseEntity.ok(output);
    }

    @PatchMapping("/{gymId}")
    public ResponseEntity<GymOutput> update(@RequestHeader("x-user-id") UUID userId,
                                            @PathVariable UUID gymId,
                                            @Valid @RequestBody UpdateGymInput input) {
        var output = gymApplicationService.updateGym(userId, gymId, input);

        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{gymId}")
    public ResponseEntity<Void> deleteGym(@RequestHeader("x-user-id")  UUID userId,
            @PathVariable UUID gymId) {
        gymApplicationService.deleteGymById(userId, gymId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{gymId}/members")
    public ResponseEntity<GymOutput> addMember(@RequestHeader("x-user-id") UUID loggedInUserId,
                                             @PathVariable UUID gymId,
                                             @Valid @RequestBody AddMemberInput memberId) {

        return ResponseEntity.ok(gymApplicationService.addMember(loggedInUserId, gymId, memberId));
    }

    @DeleteMapping("/{gymId}/members/{memberId}")
    public ResponseEntity<GymOutput> removeMember(@RequestHeader("x-user-id") UUID loggedInUserId,
                                                @PathVariable UUID gymId,
                                                @PathVariable UUID memberId) {

        return ResponseEntity.ok(gymApplicationService.removeMember(loggedInUserId, gymId, memberId));
    }

    @PostMapping("/{gymId}/gym-classes")
    public ResponseEntity<GymOutput> addGymClass(@RequestHeader("x-user-id") UUID loggedInUserId,
                                                 @PathVariable UUID gymId,
                                                 @Valid @RequestBody AddGymClassInput input){

        return ResponseEntity.ok(gymApplicationService.addGymClass(loggedInUserId, gymId, input));
    }

    @DeleteMapping("/{gymId}/gym-classes/{gymClassId}")
    public ResponseEntity<GymOutput> removeGymClass(@RequestHeader("x-user-id") UUID loggedInUserId,
                                                    @PathVariable UUID gymId,
                                                    @PathVariable UUID gymClassId) {

        return ResponseEntity.ok(gymApplicationService.removeGymClass(loggedInUserId, gymId, gymClassId));
    }
}
