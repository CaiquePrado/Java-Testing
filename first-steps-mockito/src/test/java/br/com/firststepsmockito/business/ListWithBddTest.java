package br.com.firststepsmockito.business;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListWithBddTest {
  
  @DisplayName("Display name")
  @Test
  void testMockingList_WhenSizeIsCalled_ShouldReturn10() {

    // Given
    List<?> list  = mock(List.class);
    given(list.size()).willReturn(10);

    // When & // Then
    assertThat(list.size(), is(10));
  }

  @DisplayName("Display name")
  @Test
  void testMockingList_WhenSizeIsCalled_ShouldReturnMultipleValues() {

    // Given
    List<?> list  = mock(List.class);
    given(list.size()).willReturn(10).willReturn(20);

    // When & // Then
    assertThat(list.size(), is(10));
    assertThat(list.size(), is(20));
  }

  @DisplayName("Display name")
  @Test
  void testMockingList_WhenGetIsCalled_ShouldReturnCaique() {

    // Given
    var list  = mock(List.class);
    given(list.get(0)).willReturn("Caique");

    // When & // Then
    assertThat(list.get(0), is("Caique"));
  }

  @DisplayName("Display name")
  @Test
  void testMockingList_WhenGetIsCalledWithArgumentMatcher_ShouldReturnCaique() {

    // Given
    var list  = mock(List.class);
    // If you are using argument matchers, all arguments
    // have to be provided by matchers.
    given(list.get(anyInt())).willReturn("Caique");

    // When & // Then
    assertThat(list.get(anyInt()), is("Caique"));
  }

  @DisplayName("Display name")
  @Test
  void testMockingList_WhenThrowsAnException() {
    
    // Given
    var list = mock(List.class);

    given(list.get(anyInt())).willThrow(new RuntimeException("Foo Bar!"));

    // When & // Then
    assertThrows(RuntimeException.class, () ->{
      list.get(anyInt());
    }, () -> "Should have throw an RuntimeException");
  }
}
