package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    Set<User> users = new HashSet<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        validateEmail(user.getEmail());

        if (!users.add(user)) {
            throw new UserAlreadyExistException("User with this email already exists");
        }

        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
       validateEmail(user.getEmail());

       users.remove(user);
       users.add(user);

       return user;
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new InvalidEmailException("Invalid email " + email);
        }
    }
}
