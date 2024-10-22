package com.adp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.adp.domain.User;
import com.adp.dto.Token;
import com.adp.service.RegisterService;
import com.adp.service.TokenService;



@RestController
@RequestMapping("/registration")
public class RegisterController {
  

  @Autowired RegisterService registerService;
  @Autowired TokenService tokenService;

  @PostMapping
  public ResponseEntity<?> userRegistration(@RequestBody User user, HttpServletResponse response){
    if(registerService.saveCustomer(user)){
      Token token = tokenService.generateToken(user);
      Cookie cookie = new Cookie("user", token.getToken());
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      response.addCookie(cookie);
      
      return ResponseEntity.ok().build();

    }
    return ResponseEntity.badRequest().build();
  }

  
  @PostMapping("/admin")
  public ResponseEntity<?> userRegistrationByAdmin(@RequestBody User user){
    if(registerService.saveCustomer(user)){
      Token token = tokenService.generateToken(user);
      return ResponseEntity.ok(token);
    }
    return ResponseEntity.badRequest().body("Register fail");
  }

}
