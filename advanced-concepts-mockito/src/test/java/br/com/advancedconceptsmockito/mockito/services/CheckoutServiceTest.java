package br.com.advancedconceptsmockito.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

public class CheckoutServiceTest {
  
  @DisplayName("Display name")
  @Test
  void testMockObejctConstructor() {

    // Given
    try(MockedConstruction<PaymentProcessor> mockedPaymentProcessor = 
    mockConstruction(PaymentProcessor.class,
    (mock, context) ->{
      when(mock.chargeCustomer(anyString(), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
    })){
    CheckoutService checkoutService = new CheckoutService();

    // When
    var result = checkoutService.purchaseProduct("Mackbook Pro", "42");

    // Then
    assertEquals(BigDecimal.TEN, result);
    assertEquals(1, mockedPaymentProcessor.constructed().size());
    }
  }

  @Test
  void mockDifferentObjectConstruction() {
        try (MockedConstruction<PaymentProcessor> mocked = Mockito.mockConstruction(PaymentProcessor.class)) {

            PaymentProcessor firstInstance = new PaymentProcessor("PayPal", BigDecimal.TEN);
            PaymentProcessor secondInstance = new PaymentProcessor(true, "PayPal", BigDecimal.TEN);

            when(firstInstance.chargeCustomer(anyString(), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
            when(secondInstance.chargeCustomer(anyString(), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);

            assertEquals(BigDecimal.TEN, firstInstance.chargeCustomer("42", BigDecimal.valueOf(42)));
            assertEquals(BigDecimal.TEN, secondInstance.chargeCustomer("42", BigDecimal.valueOf(42)));
            assertEquals(2, mocked.constructed().size());
        }
    }

    @Test
    void mockDifferentObjectConstructionWithAnswer() {
        try (MockedConstruction<PaymentProcessor> mocked = Mockito.mockConstructionWithAnswer(PaymentProcessor.class,
                // Default answer for the first mock
                invocation -> new BigDecimal("500.00"),
                // Additional answer for all further mocks
                invocation -> new BigDecimal("42.00"))) {

            PaymentProcessor firstInstance = new PaymentProcessor();
            PaymentProcessor secondInstance = new PaymentProcessor();
            PaymentProcessor thirdInstance = new PaymentProcessor();

            assertEquals(new BigDecimal("500.00"), firstInstance.chargeCustomer("42", BigDecimal.ZERO));
            assertEquals(new BigDecimal("42.00"), secondInstance.chargeCustomer("42", BigDecimal.ZERO));
            assertEquals(new BigDecimal("42.00"), thirdInstance.chargeCustomer("42", BigDecimal.ZERO));
        }
    }
}
