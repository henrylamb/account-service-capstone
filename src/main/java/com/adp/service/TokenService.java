package com.adp.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.adp.domain.User;
import com.adp.dto.Token;
import com.adp.repository.UserRepository;
import com.adp.util.JWTHelper;

import java.util.Optional;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Service
public class TokenService {

  @Autowired
  UserRepository userRepository;
  
  @Value("${rsa.private-key}") RSAPrivateKey privateKey;
  @Value("${rsa.public-key}") RSAPublicKey publicKey;

  public Token generateToken(User user){
    String username = user.getName();
    long userId = user.getId();
    String role = user.getRole();
    return new Token(JWTHelper.createToken(username, userId, role, publicKey, privateKey));
  }
  
  public boolean validateUser(User user) {
    if (user.getEmail() != null && user.getPassword() != null) {
      Optional<User> optUser = getUser(user.getEmail());
      return (optUser.isPresent() && optUser.get().getPassword().equals(user.getPassword())
      && optUser.get().getEmail().equals(user.getEmail()) );
    }
    return false;
  }

  private Optional<User> getUser(String name) {
    return userRepository.findByEmail(name);
  }
}
