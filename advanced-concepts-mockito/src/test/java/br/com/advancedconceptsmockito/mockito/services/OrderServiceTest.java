package br.com.advancedconceptsmockito.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import br.com.advancedconceptsmockito.mockito.models.Order;

public class OrderServiceTest {
  
    private final OrderService service = new OrderService();
    private final UUID defaultUuid = UUID.fromString("8d8b30e3-de52-4f1c-a71c-9905a8043dac");
    private final LocalDateTime defaultLocalDateTime = LocalDateTime.of(2023, 7, 4, 15, 50, 0);

    @DisplayName("Should include random OrderId when no OrderId exists")
    @Test
    void testShouldIncludeRandonOrderId_WhenNoOrderIdExists() {
        // Given / Arrange
        try(MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
            mockedUuid.when(UUID::randomUUID).thenReturn(defaultUuid);

            // When / Act
            Order result = service.createOrder("MacBook Pro", 2L, null);

            // Then / Assert
            assertEquals(defaultUuid, result.getId());
        }
    }

    @DisplayName("Should include current time when create a new Order")
    @Test
    void testShouldIncludeCurrentTime_WhenCreateANewOrder() {
        // Given / Arrange
        try(MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic(LocalDateTime.class)) {
          mockedLocalDateTime.when(LocalDateTime::now).thenReturn(defaultLocalDateTime);

            // When / Act
            Order result = service.createOrder("MacBook Pro", 2L, null);

            // Then / Assert
            assertEquals(defaultLocalDateTime, result.getCreationDate());
        }
    }
}
