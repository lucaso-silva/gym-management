package com.lucas.gym_management.application.ports.inbound.list;

import java.util.List;

public interface ForListingUsers {
    List<ListUserOutput> listUsers(String name);
}
