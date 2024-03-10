package br.com.caique.person.controllers;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.caique.person.domain._exception.ResourceNotFoundException;
import br.com.caique.person.domain.models.Person;
import br.com.caique.person.domain.services.PersonService;

@WebMvcTest
class PersonControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  PersonService personService;

  Person person;

  @BeforeEach
   void setup() {
        // Given
        person = new Person(
            "Caique",
            "Prado",
            "caiqueprado@gmail.com.br",
            "Vitória da Conquista - BA",
            "Male");
    }
  
  
  @Test
  @DisplayName("Given Person Object when Create Person then Return Saved Person")
  void testGivenPersonObject_WhenCreatePerson_thenReturnSavedPerson() throws JsonProcessingException, Exception {

    // Given
    given(personService.create(any(Person.class))).willAnswer(
      (invocation) -> invocation.getArgument(0));

    // When
    ResultActions response = mockMvc.perform(post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(person)));
    
    // Then
    response.andDo(print()).
            andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(person.getLastName())))
            .andExpect(jsonPath("$.email", is(person.getEmail())));
  }

  @Test
  @DisplayName("Given List of Persons when findAll Persons then Return Persons List")
  void testGivenListOfPersons_WhenFindAllPersons_thenReturnPersonsList() throws JsonProcessingException, Exception{

    // Given
    Person personTwo = new Person(
            "Victor",
            "Freire",
            "Victor@gmail.com.br",
            "Vitória da Conquista - BA",
            "Male");

    List<Person> personsList = new ArrayList<>();
    personsList.add(person);
    personsList.add(personTwo);

    given(personService.findAll()).willReturn(personsList);

    // When
    ResultActions response = mockMvc.perform(get("/person"));

    // Then
    response.andDo(print()).
            andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(personsList.size())));
  }

  @Test
  @DisplayName("Given personId when findById then Return Person Object")
  void testGivenPersonId_WhenFindById_thenReturnPersonObject() throws JsonProcessingException, Exception{

    // Given
    Long personId = 1L;
    given(personService.findById(personId)).willReturn(person);

    // When
    ResultActions response = mockMvc.perform(get("/person/{id}", personId));
    
    // Then
    response.andDo(print()).
            andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(person.getLastName())))
            .andExpect(jsonPath("$.email", is(person.getEmail())));
  }

  @Test
  @DisplayName("Given Invalid PersonId when findById then Return Not Found")
  void testGivenInvalidPersonId_WhenFindById_thenReturnNotFound() throws JsonProcessingException, Exception{

    // Given
    Long personId = 1L;
    given(personService.findById(personId)).willThrow(ResourceNotFoundException.class);

    // When
    ResultActions response = mockMvc.perform(get("/person/{id}", personId));
    
    // Then
    response.andExpect(status().isNotFound())
    .andDo(print());
  }

  @Test
  @DisplayName("Given Updated Person when Update then Return Updated Person Object")
  void testGivenUpdatedPerson_WhenUpdate_thenReturnUpdatedPersonObject() throws JsonProcessingException, Exception{

    // Given
    long personId = 1L;
    given(personService.findById(personId)).willReturn(person);
    given(personService.update(any(Person.class)))
    .willAnswer((invocation) -> invocation.getArgument(0));

    // When
    Person updatedPerson = new Person(
                "Kaique",
                "Costa",
                "Kaique@email.com",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
        
        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)));

    // Then
    response.
    andExpect(status().isOk())
    .andDo(print())
    .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
    .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
    .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
  }

  @Test
  @DisplayName("Given Unexistent Person when Update then Return Not Found")
  void testGivenUnexistentPerson_WhenUpdate_thenReturnNotFound() throws JsonProcessingException, Exception {
        
    // Given
    long personId = 1L;
    given(personService.findById(personId)).willThrow(ResourceNotFoundException.class);
    given(personService.update(any(Person.class)))
        .willAnswer((invocation) -> invocation.getArgument(1));
    
    // When
    Person updatedPerson = new Person(
                "Kaique",
                "Costa",
                "Kaique@email.com",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
    
    ResultActions response = mockMvc.perform(put("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedPerson)));
    
    // Then
    response.
        andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  @DisplayName("JUnit test for Given personId when Delete then Return NotContent")
  void testGivenPersonId_WhenDelete_thenReturnNotContent() throws JsonProcessingException, Exception {
      
      // Given
      long personId = 1L;
      willDoNothing().given(personService).delete(personId);
      
      // When
      ResultActions response = mockMvc.perform(delete("/person/{id}", personId));
      
      // Then
      response.
          andExpect(status().isNoContent())
              .andDo(print());
  }
}
