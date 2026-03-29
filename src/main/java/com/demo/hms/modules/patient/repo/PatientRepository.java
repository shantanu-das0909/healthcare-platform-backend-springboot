package com.demo.hms.modules.patient.repo;

import com.demo.hms.modules.patient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // get all patients with appointments without facing N + 1 problem
    @Query("SELECT DISTINCT p FROM Patient p LEFT JOIN FETCH p.appointments")
    List<Patient> findAllWithAppointments();

    //  Fetch IDs with filtering (Sorting is handled by pageable)
    @Query("SELECT p.id FROM Patient p WHERE (:name IS NULL OR p.name LIKE %:name%)")
    Page<Long> findPatientIdsFiltered(@Param("name") String name, Pageable pageable);

    // Fetch full data for the current page
    @Query("SELECT DISTINCT p FROM Patient p LEFT JOIN FETCH p.appointments WHERE p.id IN :ids")
    List<Patient> findPatientsWithAppointmentsByIds(@Param("ids") List<Long> ids);

    // Optimized: Uses an index to jump directly to the next set of IDs
    @Query("SELECT p.id FROM Patient p WHERE p.id > :lastId ORDER BY p.id ASC")
    List<Long> findNextPatientIds(@Param("lastId") Long lastId, Pageable limit);

}
