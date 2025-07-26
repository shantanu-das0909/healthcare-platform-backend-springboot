package com.demo.hms.repository;

import com.demo.hms.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT * FROM appointment WHERE patient_id=:patientId", nativeQuery = true)
    Optional<List<Appointment>> findAppointmentByPatientId(@Param("patientId") long patientId);
}
