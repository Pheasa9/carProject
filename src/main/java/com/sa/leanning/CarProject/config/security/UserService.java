package com.sa.leanning.CarProject.config.security;

import java.util.Optional;

import org.springframework.stereotype.Service;

 
public interface UserService {
      
	Optional<AuthUser>  loadUserByUsername(String username);
}
