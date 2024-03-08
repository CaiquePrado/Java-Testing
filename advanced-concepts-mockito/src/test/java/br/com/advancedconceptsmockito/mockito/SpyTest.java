package br.com.advancedconceptsmockito.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpyTest {
  
  @DisplayName("Display name")
  @Test
  void test_When_Should() {

    // Given
    List<String> mockArrayList = spy(ArrayList.class);

    // When & // Then
    assertEquals(0, mockArrayList.size());

    when(mockArrayList.size()).thenReturn(5);
    mockArrayList.add("a");

    assertEquals(5, mockArrayList.size());
  }

  @DisplayName("Display name")
  @Test
  void testV2_When_Should() {

    // Given
    List<String> spyArrayList = spy(ArrayList.class);

    // When & // Then
    assertEquals(0, spyArrayList.size());

    spyArrayList.add("a");
    assertEquals(1, spyArrayList.size());

    spyArrayList.remove("a");
    assertEquals(0, spyArrayList.size());
  }

  @DisplayName("Display name")
  @Test
  void testV3_When_Should() {

    // Given
    List<String> spyArrayList = spy(ArrayList.class);

    // When & // Then
    assertEquals(0, spyArrayList.size());

    when(spyArrayList.size()).thenReturn(5);
    spyArrayList.add("a");
    assertEquals(5, spyArrayList.size());
  }

  @DisplayName("Display name")
  @Test
  void testV4_When_Should() {

    // Given
    List<String> spyArrayList = spy(ArrayList.class);
    spyArrayList.add("a");
    // When & // Then
    verify(spyArrayList).add("a");
    verify(spyArrayList, never()).remove(anyString());
    verify(spyArrayList, never()).clear();

  }
}
