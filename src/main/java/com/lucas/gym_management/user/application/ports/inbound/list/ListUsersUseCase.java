package com.lucas.gym_management.user.application.ports.inbound.list;

import java.util.List;

public interface ListUsersUseCase {
    List<ListUserOutput> listUsers(String name);
}
