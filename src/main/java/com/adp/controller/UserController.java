package com.adp.controller;

import com.adp.util.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {

        Long userId = JWTHelper.getUserIdFromAuthContext();

        User user = userService.getUser(userId).get();

        if("ROLE_CANDIDATE".equals(user.getRole()) && userId == id){
            return ResponseEntity.ok(userService.getUser(id));
        }

        else if ("ROLE_MANAGER".equals(user.getRole())) {
            return ResponseEntity.ok(userService.getUser(id));
        }
        return ResponseEntity.badRequest().build();
    }  

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{id}")
    public Iterable<User> getUserByIdByAdmin(@PathVariable("id") long id) {
          return userService.getAll();
    } 
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") long id) {

        Long userId = JWTHelper.getUserIdFromAuthContext();
        User user_token = userService.getUser(userId).get();

        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty() || user_token.getId() != id || !isUserValid(user)) {
            return ResponseEntity.badRequest().body("Bad Request");
        }

        user.setId(user_token.getId());
        userService.saveUser(user);
        return ResponseEntity.ok(user);
        
    } 

    @PreAuthorize("hasRole('ADMIN')")
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


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUserByAdmin(@PathVariable("id") long id){
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        userService.delete(user.get());
        return ResponseEntity.noContent().build();

    }

      @GetMapping("/manager/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable("id") long id){
        Optional<User> optionalUser = userService.getUser(id);

        if (optionalUser.isEmpty()){
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = optionalUser.get();

        //check role
        if (!"ROLE_MANAGER".equalsIgnoreCase(user.getRole())){
            return ResponseEntity.badRequest().body("User not a manager");
        }
        return ResponseEntity.ok(user);
    } 


}
