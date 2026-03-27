package com.demo.hms.modules.appointment.repo;

import com.demo.hms.modules.appointment.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    // get medical records for a specific patient
    List<MedicalRecord> findByPatientIdOrderByVisitDateDesc(long patientId);
}
