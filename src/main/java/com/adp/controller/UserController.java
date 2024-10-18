package com.adp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import com.adp.domain.User;
import com.adp.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired UserRepository repo;

    @GetMapping
    public Iterable<User> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") long id) {
          return repo.findById(id);
    }  
    
}
