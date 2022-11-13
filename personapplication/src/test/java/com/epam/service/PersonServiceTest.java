package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.convertor.PersonConvertor;
import com.epam.entity.Person;
import com.epam.exception.PersonNotFoundException;
import com.epam.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	@Mock
	PersonService personService;

	@Mock
	PersonRepository personRepository;
	
	@Captor
	ArgumentCaptor<Person> personCaptor;

	Person person;
	List<Person> persons;
	PersonConvertor personConvertor;

	@BeforeEach
	void setUp() throws Exception {
		personRepository = mock(PersonRepository.class);
		personConvertor = new PersonConvertor();

		personService=new PersonService(personRepository,personConvertor);
		
		person = new Person();
		person.setId(1);
		person.setFirstName("Sai");
		person.setLastName("Thanmayee");
		person.setAge(21);
		person.setDob("2001-02-14");
		person.setGender("female");
		person.setEmail("thanmayee167@gmail.com");
		person.setPno("8074034356");
		
		persons = new ArrayList<>();
		persons.add(person);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAllPersons() throws PersonNotFoundException {
		when(personRepository.findAll()).thenReturn(persons);
		assertEquals(1, personService.getAllPerson().size());
	}
	
	@Test
	void testGetAllPersonException() throws PersonNotFoundException {
		when(personRepository.findAll()).thenReturn(new ArrayList<>());
		assertThrows(PersonNotFoundException.class,()->personService.getAllPerson());
	}

	@Test
	void testGetPerson() throws PersonNotFoundException {
		when(personRepository.findById(anyInt())).thenReturn(Optional.of(person));
		assertEquals("Sai", personService.getPerson(1).getFirstName());
	}
	
	@Test
	void testGetPersonEception() throws PersonNotFoundException {
		assertThrows(PersonNotFoundException.class,()->personService.getPerson(1));
	}

	@Test
	void testAddPerson() {
		personService.addPerson(personConvertor.toDto(person));
		verify(personRepository,times(1)).save(personCaptor.capture());
		assertEquals(person.getFirstName(), personCaptor.getValue().getFirstName());
	}

	@Test
	void testDeletePerson() {
		personService.deletePerson(anyInt());
		verify(personRepository,times(1)).deleteById(anyInt());
	}

}
