
package com.clinic.backend.service;

import com.clinic.backend.dto.request.PatientRequest;
import com.clinic.backend.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {

    // Create a patient profile
    PatientResponse createPatient(
            String username,
            PatientRequest request
    );

    // Get the logged-in patient's profile
    PatientResponse getMyProfile(String username);

    // Update the logged-in patient's profile
    PatientResponse updateMyProfile(
            String username,
            PatientRequest request
    );

    // Get all patient records
    List<PatientResponse> getAllPatients();

    // Get a patient by ID
    PatientResponse getPatientById(Long patientId);
}
