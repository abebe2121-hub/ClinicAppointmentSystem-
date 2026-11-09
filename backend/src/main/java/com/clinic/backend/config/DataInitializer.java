package com.clinic.backend.config;

import com.clinic.backend.entity.Role;
import com.clinic.backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        createRole("ADMIN");
        createRole("RECEPTIONIST");
        createRole("DOCTOR");
        createRole("PATIENT");

        System.out.println("Default roles initialized successfully.");
    }

    private void createRole(String roleName) {

        if (!roleRepository.existsByName(roleName)) {

            Role role = new Role();
            role.setName(roleName);

            roleRepository.save(role);

            System.out.println("Created role: " + roleName);
        }
    }
}