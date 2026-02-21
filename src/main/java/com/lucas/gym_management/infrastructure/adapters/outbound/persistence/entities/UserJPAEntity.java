package com.lucas.gym_management.infrastructure.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class UserJPAEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Embedded
    private AddressJPA address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserTypeJPA userType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(name="birthday", nullable = true)
    private LocalDate birthDate;

    @Column(name="status", nullable = true)
    private Boolean activeMembership;

    @Column(nullable = true)
    private String cref;

    @Column(nullable = true)
    private String specialty;

    @Column(nullable = true)
    private String gymName;
}
