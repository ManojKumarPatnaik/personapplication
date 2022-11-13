package com.epam.aop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Period;
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
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.convertor.PersonConvertor;
import com.epam.entity.Person;
import com.epam.repository.PersonRepository;
import com.epam.service.PersonService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PersonServiceAopTest {
	
	@Mock
	PersonService personService;

	@Mock
	PersonRepository personRepository;
	
	@Captor
	ArgumentCaptor<Person> personCaptor;

	Person person;
	List<Person> persons;
	PersonConvertor personConvertor;
	
	PersonService proxyPersonService;

	@BeforeEach
	void setUp() throws Exception {
		personRepository = mock(PersonRepository.class);
		personConvertor = new PersonConvertor();

		personService=new PersonService(personRepository,personConvertor);
		
		person = new Person();
		person.setFirstName("Sai");
		person.setLastName("Thanmayee");
		person.setDob("2001-02-14");
		person.setAge(Period.between(person.getDob().toLocalDate(), LocalDate.now()).getYears());
		person.setGender("female");
		person.setEmail("thanmayee167@gmail.com");
		person.setPno("8074034356");
		
		persons = new ArrayList<>();
		persons.add(person);
		
		AspectJProxyFactory factory=new AspectJProxyFactory(personService);
		PersonServiceAop personServiceAop=new PersonServiceAop();
		factory.addAspect(personServiceAop);
		proxyPersonService=factory.getProxy();
	}


	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPersonServiceAop() throws Throwable {
		when(personRepository.findById(anyInt())).thenReturn(Optional.of(person));
		assertEquals("Sai", proxyPersonService.getPerson(1).getFirstName());	
	}

}
