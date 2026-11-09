
package com.clinic.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class PatientRequest {

    @NotBlank(message = "Gender is required")
    @Size(max = 20, message = "Gender must not exceed 20 characters")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Size(max = 30, message = "Phone must not exceed 30 characters")
    private String phone;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Size(max = 100, message = "Emergency contact must not exceed 100 characters")
    private String emergencyContact;

    public PatientRequest() {}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
