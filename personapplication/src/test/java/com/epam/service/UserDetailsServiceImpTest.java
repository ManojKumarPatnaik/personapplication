package com.epam.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.epam.convertor.PersonConvertor;
import com.epam.entity.AuthGroup;
import com.epam.entity.Person;
import com.epam.repository.AuthGroupRepository;
import com.epam.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImpTest {

	@Mock
	UserDetailsServiceImp userDetailsSevice;

	@Mock
	PersonRepository personRepository;

	@Mock
	AuthGroupRepository authGroupRepository;

	@Captor
	ArgumentCaptor<Person> personCaptor;

	Person person;
	List<Person> persons;
	PersonConvertor personConvertor;

	AuthGroup authGroup;
	List<AuthGroup> authGroups;

	@BeforeEach
	void setUp() throws Exception {
		personRepository = mock(PersonRepository.class);
		authGroupRepository = mock(AuthGroupRepository.class);
		personConvertor = new PersonConvertor();

		userDetailsSevice = new UserDetailsServiceImp(personRepository, authGroupRepository);

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

		authGroup = new AuthGroup();
		authGroup.setId(1);
		authGroup.setUsername("sai");
		authGroup.setRole("USER");

		authGroups = new ArrayList<AuthGroup>();
		authGroups.add(authGroup);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testloadUserByUsername() {

		when(personRepository.findByEmail(anyString())).thenReturn(person);
		when(authGroupRepository.findByUsername(anyString())).thenReturn(authGroups);
		assertEquals("thanmayee167@gmail.com", userDetailsSevice.loadUserByUsername("Sai").getUsername());

	}

	@Test
	void testloadUserByUsernameForException() {
		assertThrows(UsernameNotFoundException.class, () -> userDetailsSevice.loadUserByUsername("sai123"));
	}
}
