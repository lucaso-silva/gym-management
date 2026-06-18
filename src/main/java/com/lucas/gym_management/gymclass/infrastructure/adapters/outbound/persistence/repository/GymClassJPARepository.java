package com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.repository;

import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.entities.GymClassJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface GymClassJPARepository extends JpaRepository<GymClassJPAEntity, UUID> {

    @Query("""
            SELECT COUNT(gc) > 0
                FROM GymClassJPAEntity gc
                    WHERE gc.gymId = :gymId
                        AND gc.schedule.dayOfWeek = :dayOfWeek
                        AND gc.schedule.room = :room
                        AND :startTime < gc.schedule.endTime
                        AND :endTime > gc.schedule.startTime
            """)
    boolean hasScheduleConflict(@Param("gymId") UUID gymId,
                                @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                @Param("room") String room,
                                @Param("startTime") LocalTime startTime,
                                @Param("endTime") LocalTime endTime);

    @Query("""
            SELECT COUNT(gc) > 0
                FROM GymClassJPAEntity gc
                    WHERE gc.gymId = :gymId
                        AND gc.schedule.dayOfWeek = :dayOfWeek
                        AND gc.schedule.room = :room
                        AND :startTime < gc.schedule.endTime
                        AND :endTime > gc.schedule.startTime
                        AND gc.id <> :excludedGymClassId
            """)
    boolean hasScheduleConflict(@Param("gymId") UUID gymId,
                                @Param("excludedGymClassId") UUID excludedGymClassId,
                                @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                @Param("room") String room,
                                @Param("startTime") LocalTime startTime,
                                @Param("endTime") LocalTime endTime);

    boolean existsByGymIdAndInstructorId(UUID gymId, UUID instructorId);
}
