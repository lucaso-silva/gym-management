package com.lucas.gym_management.application.ports.inbound.list;

import java.util.List;

public interface ListUsersUseCase {
    List<ListUserOutput> listUsers(String name);
}
