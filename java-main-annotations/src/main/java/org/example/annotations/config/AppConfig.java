package org.example.annotations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @Configuration marks this class as a source of bean definitions.
 */
@Configuration
public class AppConfig {

    /**
     * @Bean defines a method that returns a bean to be managed by Spring.
     */
    @Bean
    public LocalDateTime startupTime() {
        return LocalDateTime.now();
    }
}