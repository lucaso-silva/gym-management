package com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.mapper.GymJPAMapper;
import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.repository.GymJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class GymRepositoryAdapter implements GymRepository {
    private final GymJPARepository gymJPARepository;

    @Override
    public Gym save(Gym gym) {
        var entity = gymJPARepository.save(GymJPAMapper.toEntity(gym));

        return GymJPAMapper.toDomain(entity);
    }

    @Override
    public Optional<Gym> findById(UUID id) {
        return gymJPARepository.findById(id)
                .map(GymJPAMapper::toDomain);
    }

    @Override
    public List<Gym> findAll() {
        return gymJPARepository.findAll().stream()
                .map(GymJPAMapper::toDomain)
                .toList();
    }
}
