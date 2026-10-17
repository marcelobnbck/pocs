package org.example.orderservice.service;

import org.example.orderservice.dto.CreateOrderRequest;
import org.example.orderservice.dto.OrderResponse;
import org.example.orderservice.exception.OrderNotFoundException;
import org.example.orderservice.model.Order;
import org.example.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService service;

    @Test
    void createTrimsCustomerAndPersists() {
        when(repository.save(org.mockito.ArgumentMatchers.any(Order.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        OrderResponse response = service.create(new CreateOrderRequest("  Alice ", new BigDecimal("199.99")));

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getCustomer()).isEqualTo("Alice");
        assertThat(response.customer()).isEqualTo("Alice");
        assertThat(response.totalAmount()).isEqualByComparingTo("199.99");
    }

    @Test
    void findAllMapsEntitiesToResponses() {
        when(repository.findAll()).thenReturn(List.of(new Order("Bob", new BigDecimal("59.90"))));

        List<OrderResponse> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).customer()).isEqualTo("Bob");
    }

    @Test
    void findByIdReturnsOrder() {
        when(repository.findById(1L)).thenReturn(Optional.of(new Order("Carol", new BigDecimal("10.00"))));

        assertThat(service.findById(1L).customer()).isEqualTo("Carol");
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessageContaining("99");
    }
}
