package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoles();

    Role findByStringId(String id);

    Role findById(Long id);

    Role findByName(String name);

    void saveRole(Role role);

}
