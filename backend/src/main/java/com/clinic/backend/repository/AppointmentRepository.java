package com.clinic.backend.repository;

import com.clinic.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByStatus(String status);

    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetween(
            Long doctorId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Appointment> findByPatientIdAndAppointmentDateTimeBetween(
            Long patientId,
            LocalDateTime start,
            LocalDateTime end
    );
}