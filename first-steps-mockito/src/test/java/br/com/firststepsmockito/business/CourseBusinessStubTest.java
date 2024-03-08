package br.com.firststepsmockito.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.firststepsmockito.service.stubs.CourseServiceStub;
import br.com.firststepsmockito.services.CourseBusiness;
import br.com.firststepsmockito.services.CourseService;

public class CourseBusinessStubTest {
  
  @DisplayName("Display name")
  @Test
  void testCoursesRelatedToSpring_WhenUsingAStub() {

    // Given
    CourseService stubService = new CourseServiceStub();
    CourseBusiness business = new CourseBusiness(stubService);

    // When
    var filteredCourses = business.retriveCoursesRelatedToSpring("Caique");

    // Then
    assertEquals(4, filteredCourses.size());
  }

  @DisplayName("Display name")
  @Test
  void testCoursesRelatedToSpring_WhenUsingFooBarStudent() {

    // Given
    CourseService stubService = new CourseServiceStub();
    CourseBusiness business = new CourseBusiness(stubService);

    // When
    var filteredCourses = business.retriveCoursesRelatedToSpring("Foo Bar");

    // Then
    assertEquals(0, filteredCourses.size());
  }
 
}
