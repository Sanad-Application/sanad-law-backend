package com.zkrallah.sanad.service.role;

import com.zkrallah.sanad.entity.Role;

import java.util.Optional;

public interface RoleService {
    Role saveRole(Role role);

    Optional<Role> getRole(String role);
}
