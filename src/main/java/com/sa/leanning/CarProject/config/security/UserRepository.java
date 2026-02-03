package com.sa.leanning.CarProject.config.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sa.leanning.CarProject.Entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
     Optional<User> findByUsername(String username);
     
}
