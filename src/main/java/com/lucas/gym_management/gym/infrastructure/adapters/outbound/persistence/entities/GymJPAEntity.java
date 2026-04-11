package com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="gyms")
public class GymJPAEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @ElementCollection(targetClass = UUID.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "gym_members", joinColumns = @JoinColumn(name = "gym_id"))
    @Column(name = "member_id", nullable = false)
    private Set<UUID> membersIds = new HashSet<>();

    @ElementCollection(targetClass = UUID.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "gym_classes", joinColumns = @JoinColumn(name = "gym_id"))
    @Column(name = "class_id", nullable = false)
    private Set<UUID> gymClassesIds = new HashSet<>();

    @Embedded
    private GymAddressJPA address;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
