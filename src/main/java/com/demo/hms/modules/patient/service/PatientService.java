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
import org.springframework.stereotype.Service;

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
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }
    public Appointment addNewAppointment(NewAppointmentRequest newAppointmentRequest, Long patientId) {

        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            Appointment newAppointment = Appointment.builder()
                    .appointmentTime(newAppointmentRequest.getAppointmentTime())
                    .reasonForVisit(newAppointmentRequest.getReasonForVisit())
                    .comments(newAppointmentRequest.getComments())
                    .status(AppointmentStatus.PENDING)
                    .patient(patient)
                    .build();

            if (newAppointmentRequest.getDoctorId() != null) {
                Optional<Doctor> optionalDoctor = doctorRepository.findById(newAppointmentRequest.getDoctorId());
                optionalDoctor.orElseThrow(
                        () -> new ResourceNotFoundException("Doctor not found with this id: " + newAppointmentRequest.getDoctorId())
                );
                newAppointment.setDoctor(optionalDoctor.get());
            }
            Appointment savedAppointment = appointmentRepository.save(newAppointment);
            patient.getAppointments().add(savedAppointment);
            patientRepository.save(patient);

            return savedAppointment;

        } else {
            throw new ResourceNotFoundException("Patient not found with this id: " + patientId);
        }
    }

    public Patient addNewPatient(AddPatientRequest addPatientRequest) {
        Patient patient = Patient.builder()
                .name(addPatientRequest.getName())
                .email(addPatientRequest.getEmail())
                .build();
        return patientRepository.save(patient);
    }

}
