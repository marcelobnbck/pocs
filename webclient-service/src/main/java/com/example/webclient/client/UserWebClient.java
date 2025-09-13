package com.example.webclient.client;

import com.example.webclient.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserWebClient {

    private final WebClient webClient;

    public UserWebClient(WebClientProvider provider) {
        this.webClient = provider.get("https://jsonplaceholder.typicode.com");
    }

    public Mono<User> fetchUser(String id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }
}