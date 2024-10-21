package com.adp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adp.domain.User;
import com.adp.dto.Token;
import com.adp.service.TokenService;

@RestController
@RequestMapping("/login")
public class LoginController {
  @Autowired
  TokenService tokenService;

  @PostMapping
  public ResponseEntity<?> getToken(@RequestBody User user){
    boolean isValid = tokenService.validateUser(user);
    System.out.println("post login");
    if (isValid) {
      System.out.print("user is valid");
      Token token = tokenService.generateToken(user);
      return ResponseEntity.ok(token);
    } else {
      return ResponseEntity.status(401).body("Invalid credentials");
    }
  }
}
