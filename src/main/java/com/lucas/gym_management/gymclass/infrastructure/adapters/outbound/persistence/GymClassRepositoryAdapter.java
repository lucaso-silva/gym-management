package com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.mapper.GymClassJPAMapper;
import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.repository.GymClassJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class GymClassRepositoryAdapter implements GymClassRepository {
    private final GymClassJPARepository gymClassJPARepository;

    @Override
    public GymClass save(GymClass gymClass) {
        var entity = gymClassJPARepository.save(GymClassJPAMapper.toEntity(gymClass));

        return GymClassJPAMapper.toDomain(entity);
    }

    @Override
    public Optional<GymClass> findById(UUID id) {
        return gymClassJPARepository.findById(id)
                .map(GymClassJPAMapper::toDomain);
    }

    @Override
    public List<GymClass> findAll() {
        return gymClassJPARepository.findAll().stream()
                .map(GymClassJPAMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID gymClassId) {
        gymClassJPARepository.deleteById(gymClassId);
    }

    @Override
    public boolean hasScheduleConflict(UUID gymId, DayOfWeek dayOfWeek, String room, LocalTime startTime, LocalTime endTime) {

        return gymClassJPARepository.hasScheduleConflict(gymId,
                dayOfWeek,
                room,
                startTime,
                endTime);
    }

    @Override
    public boolean hasScheduleConflict(UUID gymId, UUID gymClassId, DayOfWeek dayOfWeek, String room, LocalTime startTime, LocalTime endTime) {

        return gymClassJPARepository.hasScheduleConflict(gymId,
                gymClassId,
                dayOfWeek,
                room,
                startTime,
                endTime);
    }
}
