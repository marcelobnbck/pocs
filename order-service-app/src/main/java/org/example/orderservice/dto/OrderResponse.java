package org.example.orderservice.dto;

import org.example.orderservice.model.Order;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(Long id, String customer, BigDecimal totalAmount, Instant createdAt) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(), order.getCustomer(), order.getTotalAmount(), order.getCreatedAt());
    }
}
