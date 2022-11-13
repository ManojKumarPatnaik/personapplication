package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.PersonDto;
import com.epam.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@GetMapping("/{id}")
	@Operation(description = "Fetches Person with PersonId")
	@ApiResponse(responseCode = "200", description = "Ok")
	@ApiResponse(responseCode = "400", description = "Bad Request")
	public ResponseEntity<PersonDto> getPerson(@PathVariable("id") int id) {
		return ResponseEntity.ok(personService.getPerson(id));
	}

	@GetMapping
	@Operation(description = "Fetches All Persons")
	@ApiResponse(responseCode = "200", description = "Ok")
	@ApiResponse(responseCode = "400", description = "Bad Request")
	public ResponseEntity<List<PersonDto>> getAllPerson() {
		return ResponseEntity.ok(personService.getAllPerson());
	}

	@PostMapping
	@Operation(description = "Add An Person")
	@ApiResponse(responseCode = "200", description = "Ok")
	@ApiResponse(responseCode = "400", description = "Bad Request")
	public ResponseEntity<String> addPerson(@RequestBody PersonDto person) {
		personService.addPerson(person);
		return ResponseEntity.ok("Person Added Successfully");
	}

	@PutMapping("/{id}")
	@Operation(description = "Update An Person")
	@ApiResponse(responseCode = "200", description = "Ok")
	@ApiResponse(responseCode = "400", description = "Bad Request")
	public ResponseEntity<String> updatePerson(@RequestBody PersonDto personDto, @PathVariable("id") int id) {
		personDto.setId(id);
		personService.addPerson(personDto);
		return ResponseEntity.ok("Person Updated Successfully");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(description = "Delete An Person (only by ADMIN)")
	@ApiResponse(responseCode = "200", description = "Ok")
	@ApiResponse(responseCode = "400", description = "Bad Request")
	public ResponseEntity<String> deletePerson(@PathVariable("id") int id) {
		personService.deletePerson(id);
		return ResponseEntity.ok("Person Deleted Successfully");

	}
}
