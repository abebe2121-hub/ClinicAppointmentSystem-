
package com.clinic.backend.controller;

import com.clinic.backend.dto.request.PatientRequest;
import com.clinic.backend.dto.response.PatientResponse;
import com.clinic.backend.service.PatientService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Create the logged-in patient's profile
    @PostMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponse> createMyProfile(
            Authentication authentication,
            @Valid @RequestBody PatientRequest request) {

        PatientResponse response = patientService.createPatient(
                authentication.getName(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get the logged-in patient's profile
    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponse> getMyProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                patientService.getMyProfile(authentication.getName())
        );
    }

    // Update the logged-in patient's profile
    @PutMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponse> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody PatientRequest request) {

        return ResponseEntity.ok(
                patientService.updateMyProfile(
                        authentication.getName(), request)
        );
    }

    // Get all patients (Admin and Receptionist)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {

        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // Get a patient by ID (Admin and Receptionist)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<PatientResponse> getPatientById(
            @PathVariable Long id) {

        return ResponseEntity.ok(patientService.getPatientById(id));
    }
}
