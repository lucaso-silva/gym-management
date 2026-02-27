package com.lucas.gym_management.application.ports.outbound.repository;

import com.lucas.gym_management.application.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    List<User> findAll();

    List<User> findByNameLike(String name);

    Optional<User> findById(UUID id);

    Optional<User> findByLogin(String login);

    User updateUser(User user);

    void deleteById(UUID id);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByEmailIdNot(String email, UUID id);
}
