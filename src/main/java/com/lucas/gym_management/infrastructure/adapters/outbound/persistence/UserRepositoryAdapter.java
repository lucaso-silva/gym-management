package com.lucas.gym_management.infrastructure.adapters.outbound.persistence;

import com.lucas.gym_management.application.domain.model.User;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.infrastructure.adapters.outbound.persistence.mapper.UserJPAMapper;
import com.lucas.gym_management.infrastructure.adapters.outbound.persistence.repository.UserJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserJPARepository userJPARepository;

    @Override
    public User save(User user) {
        var savedUserEntity = userJPARepository.save(UserJPAMapper.toEntity(user));

        return UserJPAMapper.toDomain(savedUserEntity);
    }

    @Override
    public List<User> findAll() {

        return userJPARepository.findAll()
                .stream()
                .map(UserJPAMapper::toDomain)
                .toList();
    }

    @Override
    public List<User> findByNameLike(String name) {
        return userJPARepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(UserJPAMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJPARepository.findById(id)
                .map(UserJPAMapper::toDomain);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userJPARepository.findByLogin(login)
                .map(UserJPAMapper::toDomain);
    }

    @Override
    public User updateUser(User user) {
        var userEntity = userJPARepository.save(UserJPAMapper.toEntity(user));
        return UserJPAMapper.toDomain(userEntity);
    }

    @Override
    public void deleteById(UUID id) {
        userJPARepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJPARepository.existsByEmail(email);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userJPARepository.existsByLogin(login);
    }

    @Override
    public boolean existsByEmailIdNot(String email, UUID id) {
        return userJPARepository.existsByEmailAndIdNot(email, id);
    }
}
