package com.gabriel.gamebacklog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gabriel.gamebacklog.model.User;
import com.gabriel.gamebacklog.model.UserRepository;

@Configuration
public class AdminBootstrap {

    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {

            if (!userRepository.existsByUsername("admin")) {

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");

                userRepository.save(admin);
            }
        };
    }
}
