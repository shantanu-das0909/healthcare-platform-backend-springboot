package com.demo.hms.repository;

import com.demo.hms.entity.MedicalRecord;
import com.demo.hms.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    // get medical records for a specific patient
    List<MedicalRecord> findByPatientIdOrderByVisitDateDesc(long patientId);
}
