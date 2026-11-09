
package com.clinic.backend.service.impl;

import com.clinic.backend.dto.request.PatientRequest;
import com.clinic.backend.dto.response.PatientResponse;
import com.clinic.backend.entity.Patient;
import com.clinic.backend.entity.User;
import com.clinic.backend.repository.PatientRepository;
import com.clinic.backend.repository.UserRepository;
import com.clinic.backend.service.PatientService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientServiceImpl(
            PatientRepository patientRepository,
            UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PatientResponse createPatient(
            String username, PatientRequest request) {

        User user = findUser(username);

        if (!"PATIENT".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only patient accounts can create patient profiles"
            );
        }

        if (patientRepository.existsByUserId(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Patient profile already exists"
            );
        }

        Patient patient = new Patient();
        patient.setUser(user);
        updatePatientFields(patient, request);

        Patient savedPatient = patientRepository.save(patient);

        return mapToResponse(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getMyProfile(String username) {

        User user = findUser(username);

        Patient patient = patientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Patient profile not found"
                ));

        return mapToResponse(patient);
    }

    @Override
    public PatientResponse updateMyProfile(
            String username, PatientRequest request) {

        User user = findUser(username);

        Patient patient = patientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Patient profile not found"
                ));

        updatePatientFields(patient, request);

        Patient updatedPatient = patientRepository.save(patient);

        return mapToResponse(updatedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Patient not found with ID: " + patientId
                ));

        return mapToResponse(patient);
    }

    private User findUser(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }

    private void updatePatientFields(
            Patient patient, PatientRequest request) {

        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setPhone(request.getPhone());
        patient.setAddress(request.getAddress());
        patient.setEmergencyContact(request.getEmergencyContact());
    }

    private PatientResponse mapToResponse(Patient patient) {

        User user = patient.getUser();

        return new PatientResponse(
                patient.getId(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                patient.getGender(),
                patient.getDateOfBirth(),
                patient.getPhone(),
                patient.getAddress(),
                patient.getEmergencyContact(),
                patient.getCreatedAt()
        );
    }
}
