package com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.mapper.GymClassJPAMapper;
import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.repository.GymClassJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
        return Optional.empty();
    }

    @Override
    public List<GymClass> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(UUID gymClassId) {

    }
}
