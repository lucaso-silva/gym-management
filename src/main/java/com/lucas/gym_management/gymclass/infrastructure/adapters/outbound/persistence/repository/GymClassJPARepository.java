package com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.repository;

import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.entities.GymClassJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GymClassJPARepository extends JpaRepository<GymClassJPAEntity, UUID> {
}
