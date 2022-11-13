
package com.epam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Person Application", version = "1.0",description = "Person Catalog"))
public class PersonapplicationApplication //implements CommandLineRunner 
{

	/*
	 * @Autowired private PersonRepository personRepository;
	 * 
	 * @Autowired private AuthGroupRepository authGroupRepository;
	 */

	public static void main(String[] args) {
		SpringApplication.run(PersonapplicationApplication.class, args);

	}

	/*
	 * @Override public void run(String... args) throws Exception {
	 * 
	 * PasswordEncoder encoder = new BCryptPasswordEncoder(11);
	 * 
	 * Person person = new Person(); person.setFirstName("Sai");
	 * person.setLastName("Thanmayee"); person.setDob("2001-02-14");
	 * person.setAge(Period.between(person.getDob().toLocalDate(),
	 * LocalDate.now()).getYears()); person.setGender("female");
	 * person.setEmail("thanmayee167@gmail.com"); person.setPno("8074034356");
	 * person.setPassword(encoder.encode(person.getDob().toString()));
	 * personRepository.save(person);
	 * 
	 * AuthGroup authGroup = new AuthGroup(); authGroup.setUsername("Raju");
	 * authGroup.setAuthGroup("ADMIN"); authGroupRepository.save(authGroup);
	 * 
	 * AuthGroup authGroup1 = new AuthGroup(); authGroup1.setUsername("Sai");
	 * authGroup1.setAuthGroup("USER"); authGroupRepository.save(authGroup1);
	 * 
	 * 
	 * }
	 */
}
