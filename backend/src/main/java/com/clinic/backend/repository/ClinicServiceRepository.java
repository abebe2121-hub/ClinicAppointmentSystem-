package com.clinic.backend.repository;

import com.clinic.backend.entity.ClinicService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClinicServiceRepository extends JpaRepository<ClinicService, Long> {

    Optional<ClinicService> findByName(String name);

    List<ClinicService> findByActiveTrue();

    boolean existsByName(String name);
}