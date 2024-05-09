package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // Chỉ định các origin cụ thể muốn phép truy cập, hoặc "*"
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://127.0.0.1:5500");
        // Chỉ định các phương thức HTTP mà bạn muốn phép (GET, POST, PUT, DELETE, v.v.)
        config.addAllowedMethod("*");
        // Chỉ định các tiêu đề mà bạn muốn phép
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

