package com.adp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adp.domain.User;
import com.adp.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired UserRepository repo;

    @GetMapping
    public Iterable<User> getAll() {
        return repo.findAll();
      }
    
}
