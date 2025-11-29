package com.ecommerce.utilisateur_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



@EnableEurekaClient
@SpringBootApplication
public class UtilisateurServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilisateurServiceApplication.class, args);
	}

}
