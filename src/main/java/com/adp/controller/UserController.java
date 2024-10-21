package com.adp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import com.adp.domain.User;
import com.adp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired UserService userService;

    @GetMapping
    public Iterable<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") long id) {
          return userService.getUser(id);
    }  
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") long id) {
        if (user.getId()!=id || user.getName()==null || user.getPassword()==null ||
        user.getAddress()==null || user.getPhone()==null || user.getResume()==null ||
        user.getDepartment()==null || user.getRole()==null) {
            return ResponseEntity.badRequest().build();
        }
        userService.saveUser(user);
        return ResponseEntity.ok().build();
        
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        userService.delete(user.get());;
        return ResponseEntity.noContent().build();


    }

}
