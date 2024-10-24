package com.adp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.domain.User;
import com.adp.repository.UserRepository;

@Service
public class RegisterService{

  @Autowired
  UserRepository userRepository;
  public boolean saveCandidate(User user) {
    if(user.getName() != null && user.getEmail() != null && user.getPassword() != null && isEmailFree(user.getEmail())){
      user.setRole("ROLE_CANDIDATE");
      user = userRepository.save(user);
      return user != null;
    }
    return false;
  }

  public boolean saveManager(User user) {
    if(user.getName() != null && user.getEmail() != null && user.getPassword() != null && isEmailFree(user.getEmail())){
      user.setRole("ROLE_MANAGER");
      user = userRepository.save(user);
      return user != null;
    }
    return false;
  }


  private boolean isEmailFree(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    return user.isEmpty();
  }

}
