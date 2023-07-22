package com.example.enterpriseproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.enterpriseproject.model.Role;
import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.repository.UserRepository;

@Configuration
class LoadDatabase {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadDatabase.class);

    // load database with admin user
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        // check if admin user exists
        if (userRepository.findByEmail("admin@gmail.com") != null) {
            return null;
        }
        return args -> {
            log.info(("Preloading "),
                    userRepository
                            .save(new User("admin@gmail.com", passwordEncoder.encode("admin1234"), "System", "Admin",
                                    "Admin", Role.ADMIN)));

        };

    }

}
