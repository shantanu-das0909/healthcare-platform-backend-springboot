package com.demo.hms.modules.patient.service;

import com.demo.hms.modules.patient.dto.AddPatientRequest;
import com.demo.hms.modules.appointment.dto.NewAppointmentRequest;
import com.demo.hms.modules.appointment.entity.Appointment;
import com.demo.hms.modules.appointment.entity.AppointmentStatus;
import com.demo.hms.modules.doctor.entity.Doctor;
import com.demo.hms.modules.patient.entity.Patient;
import com.demo.hms.exceptions.ResourceNotFoundException;
import com.demo.hms.modules.appointment.repo.AppointmentRepository;
import com.demo.hms.modules.doctor.repo.DoctorRepository;
import com.demo.hms.modules.patient.repo.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient addNewPatient(AddPatientRequest addPatientRequest) {
        Patient patient = Patient.builder()
                .name(addPatientRequest.getName())
                .email(addPatientRequest.getEmail())
                .build();
        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public List<Patient> getNextPageForPatient(Long lastSeenId, int pageSize) {
        // Get next IDs using the cursor (No OFFSET used)
        List<Long> ids = patientRepository.findNextPatientIds(
                lastSeenId == null ? 0L : lastSeenId,
                PageRequest.of(0, pageSize)
        );

        if (ids.isEmpty()) return List.of();

        // Fetch full data
        List<Patient> patients = patientRepository.findPatientsWithAppointmentsByIds(ids);

        // Ensure the order matches the IDs
        patients.sort(Comparator.comparing(Patient::getId));
        return patients;
    }
}
