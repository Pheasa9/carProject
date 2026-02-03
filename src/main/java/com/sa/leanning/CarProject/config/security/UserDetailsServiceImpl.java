package com.sa.leanning.CarProject.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService; // ✅ injected by constructor

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userService.loadUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
