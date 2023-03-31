package com.example.enterpriseproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EnterpriseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseProjectApplication.class, args);
	}

}
