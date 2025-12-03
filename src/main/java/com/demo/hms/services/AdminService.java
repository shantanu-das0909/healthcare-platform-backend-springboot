package com.demo.hms.services;

import com.demo.hms.dto.AddDoctorRequest;
import com.demo.hms.dto.UpdateAppointmentRequest;
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
public class AdminService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Doctor getDoctor(Long doctorId) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        optionalDoctor.orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + doctorId));
        return optionalDoctor.get();
    }

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor addDoctor(AddDoctorRequest addDoctorRequest) {
        Doctor newDoctor = Doctor.builder()
                .name(addDoctorRequest.getName())
                .email(addDoctorRequest.getEmail())
                .phone(addDoctorRequest.getPhone())
                .speciality(addDoctorRequest.getSpeciality()).build();

        return doctorRepository.save(newDoctor);
    }

    public void deleteDoctor(Long doctorId) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        optionalDoctor.orElseThrow(() -> new ResourceNotFoundException("Doctor not found with this id: " + doctorId));
        doctorRepository.deleteById(doctorId);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Appointment> getAllAppointmentsForAPatient(Long patientId) {
        return appointmentRepository.findAppointmentByPatientId(patientId).get();
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public void deletePatient(Long patientId) {
        patientRepository.deleteById(patientId);
    }

    public void updateAppointment(UpdateAppointmentRequest updateAppointmentRequest, Long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        optionalAppointment.orElseThrow(
                () -> new ResourceNotFoundException("Appointment not found with this id: " + appointmentId)
        );

        Appointment appointment = optionalAppointment.get();
        if(updateAppointmentRequest.getStatus() != null && updateAppointmentRequest.getStatus().equals("CANCEL")) {
            appointment.setStatus("CANCEL");
            appointment.setCancelReason(updateAppointmentRequest.getCancelReason());
        }

        if(updateAppointmentRequest.getDoctorId() != null &&
                !updateAppointmentRequest.getDoctorId().equals(appointment.getDoctor().getDoctorId())) {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(updateAppointmentRequest.getDoctorId());
            optionalDoctor.orElseThrow(
                    () -> new ResourceNotFoundException("Doctor not found with this id: " + updateAppointmentRequest.getDoctorId())
            );
            appointment.setDoctor(optionalDoctor.get());
        }

        if (updateAppointmentRequest.getStatus()!=null && updateAppointmentRequest.getStatus().equals("CONFIRMED")) {
            appointment.setStatus("CONFIRMED");
        }
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findAppointmentByDoctorId(doctorId).get();
    }
}
