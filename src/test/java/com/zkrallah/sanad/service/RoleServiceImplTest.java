package com.zkrallah.sanad.service;

import com.zkrallah.sanad.entity.Role;
import com.zkrallah.sanad.repository.RoleRepository;
import com.zkrallah.sanad.service.role.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRole_shouldReturnSavedRole() {
        // Given
        Role role = new Role(1L, "ROLE_ADMIN");
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // When
        Role savedRole = roleService.saveRole(role);

        // Then
        assertNotNull(savedRole);
        assertEquals(role.getName(), savedRole.getName());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void getRole_shouldReturnRoleWhenRoleExists() {
        // Given
        String roleName = "ROLE_ADMIN";
        Role role = new Role(1L, roleName);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        // When
        Optional<Role> foundRole = roleService.getRole(roleName);

        // Then
        assertTrue(foundRole.isPresent());
        assertEquals(roleName, foundRole.get().getName());
        verify(roleRepository, times(1)).findByName(roleName);
    }

    @Test
    void getRole_shouldReturnEmptyOptionalWhenRoleDoesNotExist() {
        // Given
        String roleName = "ROLE_UNKNOWN";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        // When
        Optional<Role> foundRole = roleService.getRole(roleName);

        // Then
        assertFalse(foundRole.isPresent());
        verify(roleRepository, times(1)).findByName(roleName);
    }
}
