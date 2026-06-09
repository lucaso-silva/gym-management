package com.lucas.gym_management.gymclass.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.list.ListGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.EnrollStudentInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.update.UpdateGymClassInput;
import com.lucas.gym_management.gymclass.infrastructure.service.GymClassApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{gymClassId}")
    public ResponseEntity<GymClassOutput> getGymClassById(@PathVariable UUID gymClassId){
        return ResponseEntity.ok(gymClassApplicationService.getGymClassById(gymClassId));
    }

    @GetMapping
    public ResponseEntity<List<ListGymClassOutput>> getAllGymClasses(){
        return ResponseEntity.ok(gymClassApplicationService.listGymClasses());
    }

    @DeleteMapping("/{gymClassId}")
    public ResponseEntity<Void> deleteGymClass(@PathVariable UUID gymClassId){
        gymClassApplicationService.deleteGymClassById(gymClassId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{gymClassId}/students")
    public ResponseEntity<GymClassOutput> enrollStudent(@PathVariable UUID gymClassId,
                                                        @Valid @RequestBody EnrollStudentInput input){

        return ResponseEntity.ok(gymClassApplicationService.enrollStudent(gymClassId, input));
    }

    //TODO: add endpoint for getting enrolled students
//    @GetMapping("/{gymClassId}/students")

    @PatchMapping("/{gymClassId}")
    public ResponseEntity<GymClassOutput> updateGymClass(@PathVariable
                                                         UUID gymClassId,
                                                         @Valid
                                                         @RequestBody UpdateGymClassInput input){

        return ResponseEntity.ok(gymClassApplicationService.updateGymClass(gymClassId, input));
    }

    @DeleteMapping("/{gymClassId}/students/{studentId}")
    public ResponseEntity<GymClassOutput> unenrollStudent(@PathVariable UUID gymClassId,
                                                          @PathVariable UUID studentId){

        return ResponseEntity.ok(gymClassApplicationService.unenrollStudent(gymClassId,studentId));
    }


}