package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();

    void addUser(User user, List<String> roles);

    User findUserByUserName(String name);
    User findByEmail(String email);

    void updateUser(User user, List<String> roles);

    void removeUser(Long id);

    User findOneById(Long id);
}
