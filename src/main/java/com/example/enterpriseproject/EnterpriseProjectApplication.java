package com.example.enterpriseproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.enterpriseproject.audit.SprintSecurityAuditorAware;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EnterpriseProjectApplication {

	@Bean
	public SprintSecurityAuditorAware auditorAware() {
		return new SprintSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseProjectApplication.class, args);
	}

}
