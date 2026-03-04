package com.sa.leanning.CarProject.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    

    // 2) Serve uploaded files from project-root/uploads/**
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path uploadDir = Paths.get("uploads").toAbsolutePath();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadDir.toUri().toString());
    }
}
