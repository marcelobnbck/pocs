// src/main/java/org/example/annotations/service/GreetingService.java
package org.example.annotations.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}