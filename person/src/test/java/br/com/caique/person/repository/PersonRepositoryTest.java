package br.com.caique.person.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.caique.person.domain.models.Person;
import br.com.caique.person.domain.repository.PersonRepository;

@DataJpaTest
class PersonRepositoryTest {

  @Autowired
  PersonRepository personRepository;
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
  
  @DisplayName("Given Person OBject When Save Should Return Saved Person")
  @Test
  void testGivenPersonOBject_WhenSave_ShouldReturnSavedPerson() {

    // When
    Person savedPerson = personRepository.save(person);

    // Then
    assertNotNull(savedPerson);
    assertTrue(savedPerson.getId() > 0);
  }

  @DisplayName("Given Person List When FindAll Should Return Person List")
  @Test
  void testGivenPersonList_WhenFindAll_ShouldReturnPersonList() {

    // Given

    Person personTwo = new Person("Pedro", "Ribeiro", 
    "Pedro@gmail.com", "Vitória da Conquista - BA", "male");

    personRepository.save(person);
    personRepository.save(personTwo);

    // When
    List<Person> personList = personRepository.findAll();

    // Then
    assertNotNull(personList);
    assertEquals(2, personList.size());
  }

  @DisplayName("Given Person Object When FindById Should Return Person Object")
  @Test
  void testGivenPersonOBject_WhenFindById_ShouldReturnPersonObject() {

    // Given
    personRepository.save(person);

    // When
    Person savedPerson = personRepository.findById(person.getId()).get();

    // Then
    assertNotNull(savedPerson);
    assertEquals(person.getId(), savedPerson.getId());
  }

  @DisplayName("Given Person Object When FindByEmail Should Return Person Object")
  @Test
  void testGivenPersonOBject_WhenFindByEmail_ShouldReturnPersonObject() {

    // Given
    personRepository.save(person);

    // When
    Person savedPerson = personRepository.findByEmail(person.getEmail()).get();

    // Then
    assertNotNull(savedPerson);
    assertEquals(person.getEmail(), savedPerson.getEmail());
  }

  @DisplayName("Given Person Object When Update Person Should Return Person Object")
  @Test
  void testGivenPersonOBject_WhenUpdatePerson_ShouldReturnPersonObject() {

    // Given
    personRepository.save(person);

    // When
    Person savedPerson = personRepository.findById(person.getId()).get();
    savedPerson.setFirstName("Kaique");
    savedPerson.setEmail("Kaiquepradop80@gmail.com");
    savedPerson.setAddress("Feira de Santana - BA");

    Person updatededPerson = personRepository.save(savedPerson);

    // Then
    assertNotNull(updatededPerson);
    assertEquals("Kaique", updatededPerson.getFirstName());
    assertEquals("Kaiquepradop80@gmail.com", updatededPerson.getEmail());
    assertEquals("Feira de Santana - BA", updatededPerson.getAddress());
  }

  @DisplayName("Given Person Object When Delete Person Should Remove Person ")
  @Test
  void testGivenPersonOBject_WhenDeletePerson_ShouldReturnRemovePerson() {

    // Given
    personRepository.save(person);

    // When
    personRepository.deleteById(person.getId());
    Optional<Person> personOptional = personRepository.findById(person.getId());
    
    // Then
    assertTrue(personOptional.isEmpty());
  }

  @DisplayName("Given FirstName And Lastname When FindByJPQL Should Person Object ")
  @Test
  void testGivenFirstNameAndLastname_WhenFindByJPQL_ShouldReturnPersonObject() {

    // Given
    personRepository.save(person);

    String firstName = "Caique";
    String lastName = "Prado";

    // When
    Person savedPerson = personRepository.findByJPQL(firstName, lastName);
    
    // Then
    assertNotNull(savedPerson);
    assertEquals(firstName, savedPerson.getFirstName());
    assertEquals(lastName, savedPerson.getLastName());
  }

  @DisplayName("Given FirstName And Lastname When findByJPQLNamedParameters Should Person Object ")
  @Test
  void testGivenFirstNameAndLastname_WhenfindByJPQLNamedParameters_ShouldReturnPersonObject() {

    // Given
    personRepository.save(person);

    String firstName = "Caique";
    String lastName = "Prado";

    // When
    Person savedPerson = personRepository.findByJPQLNamedParameters(firstName, lastName);
    
    // Then
    assertNotNull(savedPerson);
    assertEquals(firstName, savedPerson.getFirstName());
    assertEquals(lastName, savedPerson.getLastName());
  }

  @DisplayName("Given FirstName And Lastname When findByNativeSQL Should Person Object ")
  @Test
  void testGivenFirstNameAndLastname_WhenfindByNativeSQL_ShouldReturnPersonObject() {

    // Given
    personRepository.save(person);

    String firstName = "Caique";
    String lastName = "Prado";

    // When
    Person savedPerson = personRepository.findByNativeSQL(firstName, lastName);
    
    // Then
    assertNotNull(savedPerson);
    assertEquals(firstName, savedPerson.getFirstName());
    assertEquals(lastName, savedPerson.getLastName());
  }

  @DisplayName("Given FirstName And Lastname When findByNativeSQLwithNamedParameters Should Person Object ")
  @Test
  void testGivenFirstNameAndLastname_WhenfindByNativeSQLwithNamedParameters_ShouldReturnPersonObject() {

    // Given
    personRepository.save(person);

    String firstName = "Caique";
    String lastName = "Prado";

    // When
    Person savedPerson = personRepository.findByNativeSQLwithNamedParameters(firstName, lastName);
    
    // Then
    assertNotNull(savedPerson);
    assertEquals(firstName, savedPerson.getFirstName());
    assertEquals(lastName, savedPerson.getLastName());
  }
}
