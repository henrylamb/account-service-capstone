package com.adp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
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

    @GetMapping("/admin/{id}")
    public Optional<User> getUserByIdByAdmin(@PathVariable("id") long id) {
          return userService.getUser(id);
    } 
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") long id) {

        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty() || user.getId() != id || !isUserValid(user)) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
        userService.saveUser(user);
        return ResponseEntity.ok(user);
        
    } 

    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateUserByAdmin(@RequestBody User user, @PathVariable("id") long id) {

        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty() || user.getId() != id || !isUserValid(user)) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
        userService.saveUser(user);
        return ResponseEntity.ok(user);
        
    }

    private boolean isUserValid(User user) {
        return user.getName() != null && user.getEmail() != null && user.getPassword() != null;
      //  && user.getAddress()!=null && user.getPhone()!=null && user.getResume()!=null
       // &&  user.getDepartment()!=null && user.getRole()!=null;
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

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUserByAdmin(@PathVariable("id") long id){
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        userService.delete(user.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/managers/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable("id") long id){
        Optional<User> optionalUser = userService.getUser(id);

        if (optionalUser.isEmpty()){
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = optionalUser.get();

        //check role
        if (!"manager".equalsIgnoreCase(user.getRole())){
            return ResponseEntity.badRequest().body("User not a manager");
        }
        return ResponseEntity.ok(user);
    } 
    

}
