package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public RegistrationService(@Lazy UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }
    @Transactional
    public void registerAdmin(User user) {
        String[] roles = new String[]{"ROLE_ADMIN"};
        setUserRoles(user, roles);
        userRepository.save(user);
    }
    @Transactional
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String[] roles = new String[]{"ROLE_USER"};
        setUserRoles(user, roles);
        userRepository.save(user);
    }

    private void setUserRoles(User user, String[] rolesNames) {
        Set<Role> rolesSet = new HashSet<>();
        for (String roleName : rolesNames) {
            Role role = roleService.findByName(roleName);
            if (role == null) {
                role = new Role(roleName);
                roleService.saveRole(role);
            }
            rolesSet.add(role);
        }
        user.setRoles(rolesSet);
    }
}
