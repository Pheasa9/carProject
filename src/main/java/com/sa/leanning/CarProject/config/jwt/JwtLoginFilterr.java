package com.sa.leanning.CarProject.config.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtLoginFilterr extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {

        try {
            LoginRequest login =
                    new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            login.getUsername(),
                            login.getPassword()
                    );

            return authenticationManager.authenticate(authToken);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        var authorities = authResult.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .signWith(JwtUtil.KEY, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("JWT GENERATED KEY HASH: " + JwtUtil.KEY.hashCode());

        response.setHeader("Authorization", "Bearer " + token);
    }
}
