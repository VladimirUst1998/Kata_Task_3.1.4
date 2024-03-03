package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;


import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<Role> getRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByStringId(String id) {
        Long LongId = Long.parseLong(id);
        return roleRepository.findById(LongId).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(String name) {
        return roleRepository.findByNameOfRole(name);
    }


    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
