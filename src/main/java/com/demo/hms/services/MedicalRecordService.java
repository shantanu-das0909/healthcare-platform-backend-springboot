package com.demo.hms.services;

import com.demo.hms.dto.MedicalRecordRequestDTO;
import com.demo.hms.entity.Appointment;
import com.demo.hms.entity.AppointmentStatus;
import com.demo.hms.entity.MedicalRecord;
import com.demo.hms.entity.Prescription;
import com.demo.hms.exceptions.ResourceNotFoundException;
import com.demo.hms.repository.AppointmentRepository;
import com.demo.hms.repository.MedicalRecordRepository;
import com.demo.hms.repository.PatientRepository;
import com.demo.hms.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public MedicalRecord createFullClinicalRecord(MedicalRecordRequestDTO dto) {

        long appointmentId = dto.getAppointmentId();

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new ResourceNotFoundException("Appointment not found with id " + appointmentId)
        );

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(appointment.getPatient())
                .doctor(appointment.getDoctor())
                .reasonForVisit(dto.getReasonForVisit())
                .clinicalNotes(dto.getClinicalNotes())
                .diagnosis(dto.getDiagnosis())
                .visitDate(LocalDateTime.now()).build();

        dto.getPrescriptions().forEach(p -> {
            Prescription prescription = Prescription.builder()
                    .medicationName(p.getMedicationName())
                    .duration(p.getDuration())
                    .dosage(p.getDosage())
                    .frequency(p.getFrequency())
                    .instruction(p.getInstruction()).build();
            medicalRecord.addPrescription(prescription);
        });

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);

        MedicalRecord record = medicalRecordRepository.save(medicalRecord);

        return record;
    }

    public List<MedicalRecord> getPatientHistory(long patientId) {

        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientIdOrderByVisitDateDesc(patientId);
        return medicalRecords;
    }
}
