package com.epam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.dto.PersonPrincipal;
import com.epam.entity.AuthGroup;
import com.epam.entity.Person;
import com.epam.repository.AuthGroupRepository;
import com.epam.repository.PersonRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
	
	@Autowired
	AuthGroupRepository authGroupRepository;
	
	@Autowired 
	PersonRepository personRepository;
	
	public UserDetailsServiceImp(PersonRepository personRepository, AuthGroupRepository authGroupRepository) {
		this.authGroupRepository=authGroupRepository;
		this.personRepository=personRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Person person = personRepository.findByEmail(email);
		if (null == person) {
			throw new UsernameNotFoundException("Cannot find username :" + email);
		}
		
		List<AuthGroup> authorities  = authGroupRepository.findByUsername(email);

		return new PersonPrincipal(person,authorities);
	}

}
