package com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.repository;

import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.entities.GymJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GymJPARepository extends JpaRepository<GymJPAEntity, UUID> {

}
