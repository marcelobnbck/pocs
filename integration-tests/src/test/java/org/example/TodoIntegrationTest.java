package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Todo;
import org.example.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Full-stack integration test that boots the app, runs against a real PostgreSQL
 * in Docker (Testcontainers), and exercises the HTTP endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("admin");

    static {
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired TodoRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        repository.save(new Todo("Buy coffee", false));
        repository.save(new Todo("Write tests", true));
    }

    @Test
    void list_shouldReturnSeededTodos() throws Exception {
        mvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].title", not(isEmptyOrNullString())));
    }

    @Test
    void create_shouldPersistAndReturn201() throws Exception {
        Todo payload = new Todo("Read a book", false);

        mvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsBytes(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Read a book")))
                .andExpect(jsonPath("$.done", is(false)));

        mvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void update_shouldModifyExistingItem() throws Exception {
        Long id = repository.findAll().get(0).getId();
        Todo updated = new Todo("Buy lots of coffee", true);

        mvc.perform(put("/api/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsBytes(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Buy lots of coffee")))
                .andExpect(jsonPath("$.done", is(true)));
    }

    @Test
    void delete_shouldRemoveAndReturn204() throws Exception {
        Long id = repository.findAll().get(0).getId();

        mvc.perform(delete("/api/todos/{id}", id))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/todos/{id}", id))
                .andExpect(status().isNotFound());
    }
}
