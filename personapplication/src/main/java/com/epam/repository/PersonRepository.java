package com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{

	Person findByEmail(String email);

}
