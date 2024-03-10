package br.com.caique.person.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.caique.person.domain._exception.ResourceNotFoundException;
import br.com.caique.person.domain.models.Person;
import br.com.caique.person.domain.repository.PersonRepository;
import br.com.caique.person.domain.services.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  @Mock
  PersonRepository personRepository;

  @InjectMocks
  PersonService personService;

  Person person;

  @BeforeEach
   void setup() {
        // Given / Arrange
        person = new Person(
            "Caique",
            "Prado",
            "caiqueprado@gmail.com.br",
            "Vitória da Conquista - BA",
            "Male");
    }

    @DisplayName("Given Person Object when Save Person then Return Person Object")
    @Test
    void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject() {
        
        // Given / Arrange
        given(personRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(personRepository.save(person)).willReturn(person);
        
        // When / Act
        Person savedPerson = personService.create(person);
        
        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Caique", savedPerson.getFirstName());
    }   
    
  
  @DisplayName("Given Existing Email when Save Person then throws Exception")
  @Test
  void testGivenExistingEmail_WhenSavePerson_ShouldReturnThrowsException() {

    // Given
    given(personRepository.findByEmail(anyString())).willReturn(Optional.of(person));

    // When
    assertThrows(ResourceNotFoundException.class, () ->{
      personService.create(person);
    });

    // Then
    verify(personRepository, never()).save(any(Person.class));
  }

  @DisplayName("Given Persons List when findAll Persons then Return Persons List")
  @Test
  void testGivenPersonsList_WhenFindAllPersons_thenReturnPersonsList() {

    // Given
    Person personTwo = new Person(
      "Caique",
      "Prado",
      "caiqueprado@gmail.com.br",
      "Vitória da Conquista - BA",
      "Male");

    given(personRepository.findAll()).willReturn(List.of(person, personTwo));

    // When
    List<Person> personsList = personService.findAll();

    // Then
    assertNotNull(personsList);
    assertEquals(2, personsList.size());
  }

  @DisplayName("Given Persons List when findAll Persons then Return Persons List")
  @Test
  void testGivenEmptyPersonsList_WhenFindAllPersons_thenReturnEmptyPersonsList() {

    // Given
    given(personRepository.findAll()).willReturn(Collections.emptyList());

    // When
    List<Person> personsList = personService.findAll();

    // Then
    assertTrue(personsList.isEmpty());
    assertEquals(0, personsList.size());
  }

  @DisplayName("Given PersonId when findById then Return Person Object")
  @Test
  void testGivenPersonId_WhenFindById_thenReturnPersonObject(){

    // Given
    given(personRepository.findById(anyLong())).willReturn(Optional.of(person));

    // When
    Person savedPerson = personService.findById(1L);

    // Then
    assertNotNull(savedPerson);
    assertEquals("Caique", savedPerson.getFirstName());
  }

  @DisplayName("Given Person Object when Update Person then Return Updated Person Object")
  @Test
  void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatedPersonObject(){

    // Given
    person.setId(1L);
    given(personRepository.findById(anyLong())).willReturn(Optional.of(person));
    person.setEmail("KaiquePrado@gmail.com");
    person.setFirstName("Kaique");

    given(personRepository.save(person)).willReturn(person);

    // When
    Person updatedPerson = personService.update(person);

    // Then
    assertNotNull(updatedPerson);
    assertEquals("Kaique", updatedPerson.getFirstName());
    assertEquals("KaiquePrado@gmail.com", updatedPerson.getEmail());
  }

  @DisplayName("Given PersonID when Delete Person then do Nothing")
  @Test
  void testGivenPersonID_WhenDeletePerson_thenDoNothing(){

    // Given
    person.setId(1L);
    given(personRepository.findById(anyLong())).willReturn(Optional.of(person));
    willDoNothing().given(personRepository).delete(person);

    // When
    personService.delete(person.getId());

    // Then
    verify(personRepository, times(1)).delete(person);
  } 
}
