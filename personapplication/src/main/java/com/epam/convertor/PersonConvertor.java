package com.epam.convertor;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.epam.dto.PersonDto;
import com.epam.entity.Person;

@Service
public class PersonConvertor {
	
	ModelMapper modelMapper = new ModelMapper();
	
	public Person toEntity(PersonDto personDto)
	{
		return modelMapper.map(personDto, Person.class);
	}
	
	public PersonDto toDto(Person person)
	{
		return modelMapper.map(person, PersonDto.class);
	}
	
	public List<PersonDto> toDtoList(List<Person> personsList)
	{
		return personsList.stream().map(person->modelMapper.map(person, PersonDto.class)).toList();
	}
}
