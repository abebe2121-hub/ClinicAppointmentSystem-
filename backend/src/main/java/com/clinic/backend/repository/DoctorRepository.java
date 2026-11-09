package com.clinic.backend.repository;

import com.clinic.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUserId(Long userId);

    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);

    boolean existsByUserId(Long userId);
}