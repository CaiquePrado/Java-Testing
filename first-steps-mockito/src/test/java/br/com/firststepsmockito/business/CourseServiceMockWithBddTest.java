package br.com.firststepsmockito.business;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import br.com.firststepsmockito.services.CourseBusiness;
import br.com.firststepsmockito.services.CourseService;

public class CourseServiceMockWithBddTest  {

  CourseService mockService;
  CourseBusiness business;
  List<String> courses;

  @BeforeEach
  void setup(){
    // Given
    mockService = mock(CourseService.class);
    business = new CourseBusiness(mockService);
    courses = Arrays.asList(
      "REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
      "Agile Desmistificado com Scrum, XP, Kanban e Trello",
      "Spotify Engineering Culture Desmistificado",
      "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
      "Docker do Zero à Maestria - Contêinerização Desmistificada",
      "Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
      "Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
      "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
      "REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
      "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
      "Microsserviços do 0 com Spring Cloud, Kotlin e Docker"
  );
  }

  @DisplayName("Display name")
  @Test
  void testCoursesRelatedToSpring_WhenUsingAStub() {

    given(mockService.retrieveCourses("Caique")).willReturn(courses);

    var filteredCourses = business.retriveCoursesRelatedToSpring("Caique");

    // Then
    assertThat(filteredCourses.size(),is(4) );
  }

  @DisplayName("Delete Courses not Related to Spring Using Mockito should call Method deleteCourse")
  @Test
  void testDeleteCoursesNotRelatedToSpring_WhenUsingMockito_ShouldCallMethodDeleteCourse() {

    // Given
    given(mockService.retrieveCourses("Caique")).willReturn(courses);

    // When
    business.deleteCoursesNotRelatedToSpring("Caique");

    // Then
    verify(mockService, times(1)).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
    verify(mockService).deleteCourse("Spotify Engineering Culture Desmistificado");
    verify(mockService, atLeast(1)).deleteCourse("Docker do Zero à Maestria - Contêinerização Desmistificada");
    verify(mockService, atLeastOnce()).deleteCourse("Docker para Amazon AWS Implante Apps Java e .NET com Travis CI");
    verify(mockService).deleteCourse("Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#");
    verify(mockService).deleteCourse("Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android");

    verify(mockService, never()).deleteCourse("REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker");
  }

  @DisplayName("Delete Courses not Related to Spring Using Mockito should call Method deleteCourse v2")
  @Test
  void testDeleteCoursesNotRelatedToSpring_WhenUsingMockito_ShouldCallMethodDeleteCourseV2() {

    // Given
    given(mockService.retrieveCourses("Caique")).willReturn(courses);

    // When
    business.deleteCoursesNotRelatedToSpring("Caique");

    // Then
    then(mockService).should(times(1)).deleteCourse("Agile Desmistificado com Scrum, XP, Kanban e Trello");
    then(mockService).should(atLeast(1)).deleteCourse("Spotify Engineering Culture Desmistificado");
    then(mockService).should(atLeastOnce()).deleteCourse("Docker do Zero à Maestria - Contêinerização Desmistificada");
    then(mockService).should().deleteCourse("Docker para Amazon AWS Implante Apps Java e .NET com Travis CI");
    then(mockService).should().deleteCourse("Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#");
    then(mockService).should().deleteCourse("Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android");

    then(mockService).should(never()).deleteCourse("REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker");
  }

  @DisplayName("Delete Courses not Related to Spring Capturing Arguments sould call Method deleteCourse v2")
  @Test
  void testDeleteCoursesNotRelatedToSpring_WhenCapturingArguments_ShouldCallMethodDeleteCourseV2() {

    // Given
    courses = Arrays.asList(
      "Agile Desmistificado com Scrum, XP, Kanban e Trello",
      "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker"
    );
  
    given(mockService.retrieveCourses("Caique")).willReturn(courses);
    ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
    String agileCourse = "Agile Desmistificado com Scrum, XP, Kanban e Trello";

    // When
    business.deleteCoursesNotRelatedToSpring("Caique");

    // Then
    then(mockService).should(times(1)).deleteCourse(agileCourse);

    then(mockService).should().deleteCourse(argumentCaptor.capture());
    assertThat(argumentCaptor.getValue(), is(agileCourse));
  }

  @DisplayName("Delete Courses not Related to Spring Capturing Arguments All Values sould call Method deleteCourse v2")
  @Test
  void testDeleteCoursesNotRelatedToSpring_WhenCapturingArgumentsAllValues_ShouldCallMethodDeleteCourseV2() {

    // Given
    given(mockService.retrieveCourses("Caique")).willReturn(courses);
    ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

    // When
    business.deleteCoursesNotRelatedToSpring("Caique");

    // Then
    then(mockService).should(times(7)).deleteCourse(argumentCaptor.capture());
    assertThat(argumentCaptor.getAllValues().size(), is(7));
  }
}

  
