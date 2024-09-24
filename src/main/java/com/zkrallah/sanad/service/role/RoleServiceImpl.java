package com.zkrallah.sanad.service.role;

import com.zkrallah.sanad.entity.Role;
import com.zkrallah.sanad.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        Optional<Role> existingRole = roleRepository.findByName(role.getName());
        return existingRole.orElseGet(() -> roleRepository.save(role));
    }

    @Override
    public Optional<Role> getRole(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
