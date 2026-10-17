package org.example.orderservice.controller;

import org.example.orderservice.dto.CreateOrderRequest;
import org.example.orderservice.dto.OrderResponse;
import org.example.orderservice.exception.OrderNotFoundException;
import org.example.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    private static OrderResponse sample(long id, String customer, String amount) {
        return new OrderResponse(id, customer, new BigDecimal(amount), Instant.parse("2026-01-01T00:00:00Z"));
    }

    @Test
    void createReturns201WithLocation() throws Exception {
        when(service.create(any(CreateOrderRequest.class))).thenReturn(sample(1, "Alice", "199.99"));

        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customer\":\"Alice\",\"totalAmount\":199.99}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/orders/1")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customer", is("Alice")))
                .andExpect(jsonPath("$.totalAmount", is(199.99)));
    }

    @Test
    void createRejectsInvalidBody() throws Exception {
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customer\":\"\",\"totalAmount\":-5}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.customer", notNullValue()))
                .andExpect(jsonPath("$.errors.totalAmount", notNullValue()));

        verifyNoInteractions(service);
    }

    @Test
    void createRejectsMalformedJson() throws Exception {
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content("{not json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("Invalid request")));
    }

    @Test
    void listReturnsOrders() throws Exception {
        when(service.findAll()).thenReturn(List.of(sample(1, "Bob", "59.90")));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customer", is("Bob")));
    }

    @Test
    void getByIdReturnsOrder() throws Exception {
        when(service.findById(1L)).thenReturn(sample(1, "Bob", "59.90"));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(service.findById(42L)).thenThrow(new OrderNotFoundException(42L));

        mockMvc.perform(get("/orders/42"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", containsString("42")));
    }

    @Test
    void getByIdReturns400ForNonNumericId() throws Exception {
        mockMvc.perform(get("/orders/abc")).andExpect(status().isBadRequest());
    }

    @Test
    void unexpectedErrorReturns500WithoutLeakingDetails() throws Exception {
        when(service.findAll()).thenThrow(new IllegalStateException("db password is hunter2"));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.detail", not(containsString("hunter2"))));
    }
}
