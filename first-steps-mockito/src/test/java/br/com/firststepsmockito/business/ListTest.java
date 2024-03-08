package br.com.firststepsmockito.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListTest {
  
  @DisplayName("Display name")
  @Test
  void testMockingList_WhenSizeIsCalled_ShouldReturn10() {

    // Given
    List<?> list  = mock(List.class);
    when(list.size()).thenReturn(10);

    // When & // Then
    assertEquals(10, list.size());
  }

  @DisplayName("Display name")
  @Test
  void testMockingList_WhenSizeIsCalled_ShouldReturnMultipleValues() {

    // Given
    List<?> list  = mock(List.class);
    when(list.size()).thenReturn(10).thenReturn(20);

    // When & // Then
    assertEquals(10, list.size());
    assertEquals(20, list.size());
  }

  @DisplayName("Display name")
  @Test
  void testMockingList_WhenGetIsCalled_ShouldReturnCaique() {

    // Given
    var list  = mock(List.class);
    when(list.get(0)).thenReturn("Caique");

    // When & // Then
    assertEquals("Caique", list.get(0));
  }

  @DisplayName("Display name")
  @Test
  void testMockingList_WhenGetIsCalledWithArgumentMatcher_ShouldReturnCaique() {

    // Given
    var list  = mock(List.class);
    // If you are using argument matchers, all arguments
    // have to be provided by matchers.
    when(list.get(anyInt())).thenReturn("Caique");

    // When & // Then
    assertEquals("Caique", list.get(anyInt()));
  }

  @DisplayName("Display name")
  @Test
  void testMockingList_WhenThrowsAnException() {
    
    // Given
    var list = mock(List.class);

    when(list.get(anyInt())).thenThrow(new RuntimeException("Foo Bar!"));

    // When & // Then
    assertThrows(RuntimeException.class, () ->{
      list.get(anyInt());
    }, () -> "Should have throw an RuntimeException");
  }
}
