package com.adp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adp.domain.User;
import com.adp.service.RegisterService;

@RestController
@RequestMapping("/register")
public class RegisterController {

  @Autowired RegisterService registerService;

  @PostMapping
  public ResponseEntity<?> userRegistration(@RequestBody User user){
    if(registerService.saveCustomer(user)){
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.badRequest().body("Register fail");
  }

}
