package com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.repository;

import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.entities.GymJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GymJPARepository extends JpaRepository<GymJPAEntity, UUID> {
    @Query("""
    SELECT COUNT(g) > 0
        FROM GymJPAEntity g
            JOIN g.membersIds m
                WHERE g.id = :gymId
                    AND m = :memberId
    """)
    boolean memberBelongsToGym(UUID gymId, UUID memberId);

    @Query("""
    SELECT COUNT(g) > 0
        FROM GymJPAEntity g
            WHERE g.address.street = :street
                AND g.address.number = :number
                AND g.address.neighborhood = :neighborhood
                AND g.address.city = :city
                AND g.address.state = :state
    """)
    boolean existsByAddress(String street, String number, String neighborhood, String city, String state);
}
