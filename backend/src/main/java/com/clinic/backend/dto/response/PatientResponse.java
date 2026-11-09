
package com.clinic.backend.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientResponse {

    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String gender;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private String emergencyContact;
    private LocalDateTime createdAt;

    public PatientResponse() {}

    public PatientResponse(
            Long id,
            Long userId,
            String username,
            String email,
            String gender,
            LocalDate dateOfBirth,
            String phone,
            String address,
            String emergencyContact,
            LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
