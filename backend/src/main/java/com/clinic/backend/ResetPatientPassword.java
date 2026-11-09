
package com.clinic.backend;

import com.clinic.backend.entity.User;
import com.clinic.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ResetPatientPassword {

    @Bean
    public CommandLineRunner resetPassword(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            User user = userRepository.findByUsername("patient01")
                    .orElseThrow(() ->
                            new RuntimeException("patient01 account not found"));

            String newPassword = "ClinicPatient2026!";

            user.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(user);

            System.out.println("Patient password reset successfully.");
        };
    }
}
