package com.clinic.backend.repository;

import com.clinic.backend.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByDoctorId(Long doctorId);

    List<Availability> findByDoctorIdAndDayOfWeek(
            Long doctorId,
            DayOfWeek dayOfWeek
    );

    List<Availability> findByAvailableTrue();
}