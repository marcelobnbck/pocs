package org.example.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RockBandApplication {
    public static void main(String[] args) {
        SpringApplication.run(RockBandApplication.class, args);
    }
}