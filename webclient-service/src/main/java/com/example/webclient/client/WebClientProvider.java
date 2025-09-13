package com.example.webclient.client;

import org.springframework.web.reactive.function.client.WebClient;

@FunctionalInterface
public interface WebClientProvider {
    WebClient get(String url);
}