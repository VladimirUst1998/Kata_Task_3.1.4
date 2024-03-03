package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;


@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<User> loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByEmail(name);
    }

    @Transactional(readOnly = true)
    public Set<Role> getUserRoles(String name) {
        Optional<User> userOptional = userRepository.findByName(name);
        return userOptional.map(User::getRoles).orElse(Collections.emptySet());
    }
}
