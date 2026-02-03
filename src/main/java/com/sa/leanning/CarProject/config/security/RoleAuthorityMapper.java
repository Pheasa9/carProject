package com.sa.leanning.CarProject.config.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sa.leanning.CarProject.Entities.Roles;

public class RoleAuthorityMapper {
    
	public Set<SimpleGrantedAuthority> getAuthorities(Set<Roles> roles){
		 Set<SimpleGrantedAuthority> authorities = roles.stream()
				 .flatMap(role-> getStream(role)).collect(Collectors.toSet());		
		 authorities.addAll(getRole(roles));
		 return authorities;
	}
	
	private Stream<SimpleGrantedAuthority> getStream(Roles role){
		return role.getPermissions().stream().map(p-> new SimpleGrantedAuthority(p.getName()));
	}
	
	private Set<SimpleGrantedAuthority> getRole(Set<Roles> roles){
	   return roles.stream().map(role ->new SimpleGrantedAuthority("Role_"+role.getName()))
	                  .collect(Collectors.toSet());
				    
	}
	
}
