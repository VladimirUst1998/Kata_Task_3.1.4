package ru.kata.spring.boot_security.demo.until;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.RoleService;


import javax.annotation.PostConstruct;

@Component
public class MyDataInitializer {

    private final AdminService adminService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationService registrationService;

    @Autowired
    public MyDataInitializer(AdminService adminService, RoleService roleService, PasswordEncoder passwordEncoder, RegistrationService registrationService) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.registrationService = registrationService;
    }

    @PostConstruct
    public void initializeData() {
        if (adminService.getAllUsers().isEmpty()) {
            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");
            roleService.saveRole(adminRole);
            roleService.saveRole(userRole);
            User adminUser = new User();
            adminUser.setName("admin");
            adminUser.setSurname("admin");
            adminUser.setEmail("admin@mail.ru");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            registrationService.registerAdmin(adminUser);
            System.out.println("Users created:");
            System.out.println("Admin: username=admin, password=admin");
        }
    }
}
