package com.sa.leanning.CarProject.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1. Web MVC CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type") 
                .allowCredentials(true);
    }

    // 2. Spring Security CORS
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // When Angular asks for /uploads/**, look in the local C: folder
        registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:///D:/Spring-Boot/image-phoneShop/");
    }
   
}
