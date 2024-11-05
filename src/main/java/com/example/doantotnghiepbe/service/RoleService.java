package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<Role> getRoleById(Long id);
    Role saveRole(Role role);
    void deleteRole(Long id);
}

