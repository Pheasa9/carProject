package com.sa.leanning.CarProject.config.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.sa.leanning.CarProject.config.security.PermissionEnum.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum RoleEnum {

    ADMIN(Set.of(BRAND_READ, BRAND_WRITE, MODEL_READ, MODEL_WRITE)),
    SALE(Set.of(MODEL_READ, BRAND_READ));

    private final Set<PermissionEnum> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    	
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getDecription()))
                .collect(Collectors.toSet());
       
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
    
    
}

