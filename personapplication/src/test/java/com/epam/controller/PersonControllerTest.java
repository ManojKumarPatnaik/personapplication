package com.epam.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.PersonDto;
import com.epam.exception.PersonNotFoundException;
import com.epam.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="abc",roles={"ADMIN"})
class PersonControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PersonService personService;

	PersonDto person;
	List<PersonDto> persons;

	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
		objectMapper = new ObjectMapper();
		SimpleDateFormat format=new SimpleDateFormat("2001-02-14");
		objectMapper.setDateFormat(format);

		person = new PersonDto();
		person.setId(1);
		person.setFirstName("Sai");
		person.setLastName("Thanmayee");
		person.setAge(21);
		person.setDob("2001-02-14");
		person.setGender("female");
		person.setEmail("thanmayee167@gmail.com");
		person.setPno("8074034356");

		persons = new ArrayList<PersonDto>();
		persons.add(person);

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetPerson() throws Exception {
		when(personService.getPerson(anyInt())).thenReturn(person);
		mockMvc.perform(get("/person/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(person))).andExpect(status().isOk());
	}

	@Test
	void testGetPersonException() throws Exception {
		when(personService.getPerson(anyInt())).thenThrow(PersonNotFoundException.class);
		mockMvc.perform(get("/person/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(person))).andExpect(status().isBadRequest());
	}

	@Test
	void testGetAllPerson() throws Exception {
		when(personService.getAllPerson()).thenReturn(persons);
		mockMvc.perform(get("/person").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(persons))).andExpect(status().isOk());
	}

	@Test
	void testGetAllPersonException() throws Exception {
		when(personService.getAllPerson()).thenThrow(PersonNotFoundException.class);
		mockMvc.perform(get("/person").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(persons))).andExpect(status().isBadRequest());
	}

	@Test
	void testAddPerson() throws Exception {
		doNothing().when(personService).addPerson(person);
		mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(person))).andExpect(status().isOk());
	}

	@Test
	void testUpdatePerson() throws Exception {
		doNothing().when(personService).addPerson(person);
		mockMvc.perform(put("/person/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(person))).andExpect(status().isOk());
	}
	
	@Test
	void testDeletePerson() throws Exception {
		doNothing().when(personService).deletePerson(anyInt());
		mockMvc.perform(delete("/person/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(person))).andExpect(status().isOk());
	}

}
