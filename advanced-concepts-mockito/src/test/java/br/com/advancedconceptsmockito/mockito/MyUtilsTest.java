package br.com.advancedconceptsmockito.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class MyUtilsTest {
  
  @DisplayName("Display name")
  @Test
  void shouldMockStaticMethodWithParams() {

    // Given
    try(MockedStatic<MyUtils> mockedStatic = mockStatic(MyUtils.class)){
      // When
      mockedStatic.when(
        () -> MyUtils.getWelcomeMessage(
            eq("Caique"),
            anyBoolean())).thenReturn("Howdy Caique!");

      String result = MyUtils.getWelcomeMessage("Caique", false);
      // Then
      assertEquals("Howdy Caique!", result);
    }
  }
}
