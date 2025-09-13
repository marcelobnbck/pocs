package com.example.webclient.client;

import com.example.webclient.model.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

class UserWebClientTest {

    private static MockWebServer mockWebServer;
    private static UserWebClient userWebClient;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        userWebClient = new UserWebClient(url -> webClient);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldFetchUserSuccessfully() {
        String body = """
                    {
                      "id": "1",
                      "name": "Leanne Graham",
                      "email": "leanne@example.com"
                    }
                    """;
        mockWebServer.enqueue(new MockResponse()
                .setBody(body)
                .addHeader("Content-Type", "application/json"));

        Mono<User> userMono = userWebClient.fetchUser("1");

        StepVerifier.create(userMono)
                .expectNextMatches(user -> user.getName().equals("Leanne Graham") &&
                                           user.getEmail().equals("leanne@example.com"))
                .verifyComplete();
    }
}
