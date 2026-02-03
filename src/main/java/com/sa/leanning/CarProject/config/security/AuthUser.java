package com.sa.leanning.CarProject.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthUser implements UserDetails {
    
	
	private String username;
	private String password; 
	private Collection<? extends GrantedAuthority> authorities; 
	private boolean accountNonExpired;
	private boolean accountNonLocked; 
	private boolean credentialsNonExpired;
	private boolean enabled;

    
  
    
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password; 
    }
     
    @Override
    public String getUsername() {
        return username;     }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
