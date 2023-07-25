package de.shgruppe.springsample.controllers;

import de.shgruppe.springsample.UserRepository;
import de.shgruppe.springsample.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRepository repository;

    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return repository.findById(id).get();
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        repository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        repository.save(user);
    }
}
