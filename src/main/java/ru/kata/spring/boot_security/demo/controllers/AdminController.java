package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.until.RoleValidator;
import ru.kata.spring.boot_security.demo.until.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final RoleService roleService;
    private final UserValidator userValidator;
    private final RoleValidator roleValidator;


    @Autowired
    public AdminController(AdminService adminService, RoleService roleService, UserValidator userValidator, RoleValidator roleValidator) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.userValidator = userValidator;
        this.roleValidator = roleValidator;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model,  Principal principal) {

        User currentUser = adminService.findByEmail(principal.getName());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", new User());
        List<User> userList = adminService.getAllUsers();
        model.addAttribute("userList", userList);
        return "admin/users";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "/admin/new";
    }

    @PostMapping("/addUser")
    public String postAddUserForm(@ModelAttribute("user") @Valid User user,
                                   BindingResult userBindingResult,
                                   @RequestParam(value = "roles", required = false) @Valid List<String> roles,
                                   BindingResult rolesBindingResult,
                                   RedirectAttributes redirectAttributes) {

        userValidator.validate(user, userBindingResult);
        if (userBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorsUser", userBindingResult.getAllErrors());
            return "redirect:/admin/users";
        }
        roleValidator.validate(roles, rolesBindingResult);
        if (rolesBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorsRoles", rolesBindingResult.getAllErrors());
            return "redirect:/admin/users";
        }
        adminService.addUser(user, roles);
        return "redirect:/admin/users";
    }


    @PostMapping("/updateUser")
    public String postEditUserForm(@ModelAttribute("user") @Valid User user,
                                   @RequestParam(value = "roles", required = false) @Valid List<String> roles,
                                   BindingResult rolesBindingResult,
                                   RedirectAttributes redirectAttributes) {

        roleValidator.validate(roles, rolesBindingResult);
        if (rolesBindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorsRoles", rolesBindingResult.getAllErrors());
            return "redirect:/admin/users";
        }

        adminService.updateUser(user, roles);
        return "redirect:/admin/users";
    }

    @PostMapping("/user/delete")
    public String delete(@RequestParam Long id) {
        adminService.removeUser(id);
        return "redirect:/admin/users";
    }

}
