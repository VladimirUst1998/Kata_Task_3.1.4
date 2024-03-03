package ru.kata.spring.boot_security.demo.until;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersService;

@Component
public class UserValidator implements Validator {

    private final UsersService usersService;


    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        User person = (User) target;
        if (usersService.loadUserByUsername(person.getName()).isPresent()
                && !usersService.loadUserByUsername(person.getName()).orElse(null).equals(target)) {
            errors.rejectValue("name", "", "A user with that name already exists");
        }
    }
}
