package com.sa.leanning.CarProject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sa.leanning.CarProject.config.jwt.JwtAuthorizationFilter;
import com.sa.leanning.CarProject.config.jwt.JwtLoginFilterr;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UserDetailsService userDetailsService,
                                           AuthenticationManager authManager) throws Exception {

        // JWT login filter (handles /login)
        JwtLoginFilterr loginFilter = new JwtLoginFilterr(authManager);
        loginFilter.setFilterProcessesUrl("/login");

        // JWT authorization filter (checks Authorization header)
        JwtAuthorizationFilter jwtAuthFilter = new JwtAuthorizationFilter();

        http
            // CORS + CSRF
            .cors().and()
            .csrf().disable()

            // Add JWT filters
            .addFilter(loginFilter)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

            // Endpoint security
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/brands").hasAuthority("brand:read")
                .requestMatchers(HttpMethod.POST, "/brands").hasAuthority("brand:write")
               
                .anyRequest().authenticated()
            );

        return http.build();
    }

    // CORS config for Angular
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200"); // Angular dev server
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization"); // JWT header exposed

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
