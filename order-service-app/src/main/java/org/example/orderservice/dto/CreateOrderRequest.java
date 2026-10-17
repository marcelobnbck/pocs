package org.example.orderservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateOrderRequest(
        @NotBlank(message = "customer is required")
        @Size(max = 120, message = "customer must have at most 120 characters")
        String customer,

        @NotNull(message = "totalAmount is required")
        @Positive(message = "totalAmount must be greater than zero")
        @Digits(integer = 17, fraction = 2, message = "totalAmount must have at most 2 decimal places")
        BigDecimal totalAmount) {
}
