package com.clinicavet.petcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.clinicavet.petcare.repository")
@EnableNeo4jRepositories(basePackages = "com.clinicavet.petcare.graph.repository")
@ComponentScan(basePackages = "com.clinicavet.petcare") // Garante que a pasta config seja lida
public class PetcareApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetcareApplication.class, args);
	}

}