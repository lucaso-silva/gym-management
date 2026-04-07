package com.lucas.gym_management.user.application.service;

import com.lucas.gym_management.user.application.domain.model.User;
import com.lucas.gym_management.user.application.ports.inbound.list.ListUsersUseCase;
import com.lucas.gym_management.user.application.ports.inbound.list.ListUserOutput;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ListUsersUseCaseImpl implements ListUsersUseCase {
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ListUserOutput> listUsers(String name) {
        List<User> userList = name == null || name.isBlank()
                ? userRepository.findAll()
                : userRepository.findByNameLike(name);

        return userList.stream().map(ListUserOutput::from).toList();
    }
}
