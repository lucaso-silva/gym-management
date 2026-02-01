package com.lucas.gym_management.application.ports.outbound.repository;

import com.lucas.gym_management.application.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User create(User user);

    List<User> findAll();

    Optional<User> findByNameLike(String name);

    Optional<User> findByLogin(String login);

    User update(User user);

    void deleteById(UUID id);

}
