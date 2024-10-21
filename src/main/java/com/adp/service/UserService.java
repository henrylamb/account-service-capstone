package com.adp.service;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adp.domain.User;
import com.adp.repository.UserRepository;

@Service
public class UserService {

    @Autowired UserRepository repo;

  public Iterable<User> getAll(){
    return repo.findAll();
  }

  public Optional<User> getUser(long id) {
    return repo.findById(id);
  }

  public URI saveUser(User user) {
    user = repo.save(user);
    return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.getId())
            .toUri();
  }

  public void delete(User user){
    repo.delete(user);
  }
    
}
