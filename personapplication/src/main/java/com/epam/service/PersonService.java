package com.epam.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.convertor.PersonConvertor;
import com.epam.dto.PersonDto;
import com.epam.entity.Person;
import com.epam.exception.PersonNotFoundException;
import com.epam.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	PersonConvertor personConvertor;
	
	public PersonService(PersonRepository personRepository, PersonConvertor personConvertor) {
		this.personRepository=personRepository;
		this.personConvertor=personConvertor;
	}

	public PersonDto getPerson(int id) {
		return personConvertor.toDto(personRepository.findById(id).orElseThrow(()->new PersonNotFoundException("Person not found with Id:"+id)));
	}

	public List<PersonDto> getAllPerson() {
		List<Person> emList= personRepository.findAll();
		if(emList.isEmpty())
		{
			throw new PersonNotFoundException("No Person is registered yet!");
		}
		return personConvertor.toDtoList(emList);
	}

	public void addPerson(PersonDto person) {
		PasswordEncoder encoder=new BCryptPasswordEncoder(11);
		person.setPassword(encoder.encode(person.getDob().toString()));
		person.setAge(Period.between(person.getDob().toLocalDate(), LocalDate.now()).getYears());
		
		personRepository.save(personConvertor.toEntity(person));		
	}

	
	public void deletePerson(int id) {
		personRepository.deleteById(id);		
	}

	
	
}
