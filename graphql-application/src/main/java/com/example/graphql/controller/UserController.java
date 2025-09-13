package com.example.graphql.controller;

import com.example.graphql.model.User;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @QueryMapping
    public String hello() {
        return "Hello from GraphQL!";
    }

    @QueryMapping
    public User user(@Argument("id") String id) {
        return new User(id, "Joao Silva", "joaosilva@example.com");
    }
}