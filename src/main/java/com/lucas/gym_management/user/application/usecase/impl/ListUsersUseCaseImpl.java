package com.lucas.gym_management.user.application.usecase.impl;

import com.lucas.gym_management.user.application.domain.model.User;
import com.lucas.gym_management.user.application.ports.inbound.list.ListUserOutput;
import com.lucas.gym_management.user.application.ports.inbound.list.ListUsersUseCase;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListUsersUseCaseImpl implements ListUsersUseCase {
    private final UserRepository userRepository;

    @Override
    public List<ListUserOutput> listUsers(String name) {
        List<User> userList = name == null || name.isBlank()
                ? userRepository.findAll()
                : userRepository.findByNameLike(name);

        return userList.stream().map(ListUserOutput::from).toList();
    }
}
