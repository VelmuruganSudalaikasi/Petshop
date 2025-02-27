package com.treasuremount.petshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("**",
                        "http://localhost:3001",
                        "http://localhost:8090","https://pet-admin.netlify.app/",
                        "https://pet-website-test.netlify.app/",
                        "https://treasuremount1.netlify.app/",
                        "https://treasurmount.vercel.app/",
                        "https://treasuremount-admin.netlify.app/",
                        "https://treasuremount-website.vercel.app/")  // Specify your frontend's origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")  // Allow specific HTTP methods
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}
