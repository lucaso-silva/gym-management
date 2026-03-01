package com.lucas.gym_management.infrastructure.adapters.outbound.persistence.repository;

import com.lucas.gym_management.infrastructure.adapters.outbound.persistence.entities.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJPARepository extends JpaRepository<UserJPAEntity, UUID> {
    List<UserJPAEntity> findByNameContainingIgnoreCase(String name);

    Optional<UserJPAEntity> findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByEmailAndIdNot(String email, UUID id);
}
