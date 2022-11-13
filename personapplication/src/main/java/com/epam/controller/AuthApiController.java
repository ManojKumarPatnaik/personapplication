package com.epam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.AuthenticationRequest;
import com.epam.dto.AuthenticationResponse;
import com.epam.exception.PersonNotFoundException;
import com.epam.service.UserDetailsServiceImp;
import com.epam.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class AuthApiController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImp userDetailsServiceImp;
	
	@PostMapping(value = "/person/authenticate")
	@Operation(description = "Athenticates credentials and provides JWT")
	@ApiResponse(responseCode = "200", description = "Ok")
	@ApiResponse(responseCode = "400", description = "Bad Request")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		
		System.out.println(authenticationRequest);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}catch(BadCredentialsException be) {
			throw new PersonNotFoundException("Incorrect username and password.");
		}
		
		final UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
}
