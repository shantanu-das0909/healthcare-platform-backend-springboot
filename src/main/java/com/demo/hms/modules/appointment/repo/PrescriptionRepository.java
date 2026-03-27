package com.demo.hms.modules.appointment.repo;

import com.demo.hms.modules.appointment.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
