package com.adp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.adp.domain.User;
import com.adp.dto.Token;
import com.adp.service.TokenService;

@RestController
@RequestMapping("/login")
public class LoginController {
  @Autowired
  TokenService tokenService;

  @PostMapping
  public ResponseEntity<?> getToken(@RequestBody User user, HttpServletResponse response){
    boolean isValid = tokenService.validateUser(user);
    if (isValid) {
      Token token = tokenService.generateToken(user);
      System.out.println(token);
      Cookie cookie = new Cookie("user", token.getToken());
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      response.addCookie(cookie);
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
