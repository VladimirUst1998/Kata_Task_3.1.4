package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;



import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public void addUser(User user, List<String> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roleSet = roles.stream()
                .map(Long::valueOf)
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        user.setRoles(roleSet);
        userRepository.save(user);
    }



    @Transactional(readOnly = true)
    @Override
    public User findUserByUserName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User " + name + " not found");
        return user.get();
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return user.get();
    }

    @Transactional
    @Override
    public void updateUser(User user, List<String> roles) {
        User beforeUpdate = userRepository.getById(user.getId());
        user.setPassword(beforeUpdate.getPassword());
        Set<Role> roleSet = roles.stream()
                .map(Long::valueOf)
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        user.setRoles(roleSet);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public User findOneById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return user.get();
    }
}
