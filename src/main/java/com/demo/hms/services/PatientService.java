package com.demo.hms.services;

import com.demo.hms.dto.AddPatientRequest;
import com.demo.hms.dto.NewAppointmentRequest;
import com.demo.hms.entity.Appointment;
import com.demo.hms.entity.Doctor;
import com.demo.hms.entity.Patient;
import com.demo.hms.exceptions.ResourceNotFoundException;
import com.demo.hms.repository.AppointmentRepository;
import com.demo.hms.repository.DoctorRepository;
import com.demo.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment addNewAppointment(NewAppointmentRequest newAppointmentRequest, Long patientId) {

        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            Appointment newAppointment = Appointment.builder()
                    .appointmentDate(newAppointmentRequest.getAppointmentDate())
                    .appointmentReason(newAppointmentRequest.getAppointmentReason())
                    .comments(newAppointmentRequest.getComments())
                    .status("PENDING")
                    .patient(patient)
                    .build();

            if (newAppointmentRequest.getDoctorId() != null) {
                Optional<Doctor> optionalDoctor = doctorRepository.findById(newAppointmentRequest.getDoctorId());
                optionalDoctor.orElseThrow(
                        () -> new ResourceNotFoundException("Doctor not found with this id: " + newAppointmentRequest.getDoctorId())
                );
                newAppointment.setDoctor(optionalDoctor.get());
            }

            patient.getAppointments().add(newAppointment);
            patientRepository.save(patient);

            return null;

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
