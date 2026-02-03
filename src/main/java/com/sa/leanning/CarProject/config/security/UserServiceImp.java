package com.sa.leanning.CarProject.config.security;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sa.leanning.CarProject.Entities.User;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

@Server
@RequiredArgsConstructor
@Primary
public class UserServiceImp implements UserService{
    
	private final UserRepository userRepository;
	private final RoleAuthorityMapper roleAuthorityMapper;
	
	@Override
	public Optional<AuthUser> loadUserByUsername(String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(()-> 
		                                          new UsernameNotFoundException(username));
		AuthUser authUser = AuthUser.builder()
				                    .username(user.getUsername())
				                    .password(user.getPassword())
				                    .authorities(roleAuthorityMapper.getAuthorities(user.getRoles()))
				                    .accountNonExpired(user.isAccountNonExpired())
				                    .accountNonLocked(user.isAccountNonLocked())
				                    .credentialsNonExpired(user.isCredentialsNonExpired())
				                    .enabled(user.isEnabled())
				                    .build();
				
		
		return Optional.ofNullable(authUser);
	}

	

}
