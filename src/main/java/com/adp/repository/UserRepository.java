package com.adp.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.adp.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
  Iterable<User> findAll();

  Optional<User> findByEmail(String email);
}
