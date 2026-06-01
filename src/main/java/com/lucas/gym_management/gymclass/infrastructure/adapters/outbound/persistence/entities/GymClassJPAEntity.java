package com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="gym_classes")
public class GymClassJPAEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "gym_id", nullable = false)
    private UUID gymId;

    @Column(name = "instructor_id", nullable = false)
    private UUID instructorId;

    @Column(nullable = false)
    private Integer capacity;

    @Builder.Default
    @ElementCollection(targetClass = UUID.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "gym_class_students", joinColumns = @JoinColumn(name = "gym_class_id"))
    @Column(name= "student_id",nullable = false)
    private Set<UUID> enrolledStudents = new HashSet<>();

    @Embedded
    private ScheduleJPA schedule;
}
