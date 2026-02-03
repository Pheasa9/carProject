package com.sa.leanning.CarProject.config.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            System.out.println("JWT VERIFY KEY HASH: " + JwtUtil.KEY.hashCode());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            List<SimpleGrantedAuthority> authorities =
                    ((List<String>) claims.get("authorities"))
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            authorities
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            System.out.println("JWT VERIFY ERROR: " + e.getMessage());
        }

        chain.doFilter(request, response);
    }
}
