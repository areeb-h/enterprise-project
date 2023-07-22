package com.example.enterpriseproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.enterpriseproject.audit.SprintSecurityAuditorAware;

@SpringBootApplication
// @EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories
public class EnterpriseProjectApplication {

	@Bean
	public SprintSecurityAuditorAware auditorAware() {
		return new SprintSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseProjectApplication.class, args);
	}

}
