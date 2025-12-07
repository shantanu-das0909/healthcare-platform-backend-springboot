package com.demo.hms.repository;

import com.demo.hms.entity.Appointment;
import com.demo.hms.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT * FROM appointment WHERE patient_id=:patientId", nativeQuery = true)
    Optional<List<Appointment>> findAppointmentByPatientId(@Param("patientId") long patientId);

    @Query(value = "SELECT * FROM appointment WHERE doctor_id=:doctorId", nativeQuery = true)
    Optional<List<Appointment>> findAppointmentByDoctorId(@Param("doctorId") long doctorId);

    // Custom query to check for overlapping appointments for a specific doctor
    List<Appointment> findByDoctorAndAppointmentTimeBetween(
            Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);

    // Get appointments for a specific doctor on a specific day
    List<Appointment> findByDoctorAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(
            Doctor doctor, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
