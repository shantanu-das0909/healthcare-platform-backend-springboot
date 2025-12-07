package com.demo.hms.services;

import com.demo.hms.dto.NewAppointmentRequest;
import com.demo.hms.entity.Appointment;
import com.demo.hms.entity.AppointmentStatus;
import com.demo.hms.entity.Doctor;
import com.demo.hms.entity.Patient;
import com.demo.hms.exceptions.ResourceNotFoundException;
import com.demo.hms.repository.AppointmentRepository;
import com.demo.hms.repository.DoctorRepository;
import com.demo.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public List<LocalDateTime> getDoctorAvailability(Long doctorId, LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        LocalTime startHour = LocalTime.of(9, 0);
        LocalTime endHour = LocalTime.of(17, 0);
        int appointmentTimeDuration = 30;

        LocalDateTime startDay = date.atTime(startHour);
        LocalDateTime endDay = date.atTime(endHour);

        List<Appointment> existingAppointments = appointmentRepository
                .findByDoctorAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(doctor, startDay, endDay);

        Set<LocalDateTime> bookedTimes = existingAppointments.stream()
                .map(Appointment::getAppointmentTime).collect(Collectors.toSet());

        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime currentSlot = startDay;

        while(currentSlot.isBefore(endDay)) {
            if(!bookedTimes.contains(currentSlot)) {
                availableSlots.add(currentSlot);
            }
            currentSlot = currentSlot.plusMinutes(appointmentTimeDuration);
        }

        return availableSlots;
    }

    public Appointment bookAppointment(NewAppointmentRequest appointmentRequest) {
        int appointmentTimeDuration = 30;
        Long doctorId = appointmentRequest.getDoctorId();
        Long patientId = appointmentRequest.getPatientId();
        LocalDateTime appointmentTime = appointmentRequest.getAppointmentTime();

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        List<Appointment> conflictingAppointments = appointmentRepository.findByDoctorAndAppointmentTimeBetween(
                doctor, appointmentTime, appointmentTime.plusMinutes(appointmentTimeDuration));

        if(!conflictingAppointments.isEmpty()) {
            throw new ResourceNotFoundException("Slot already booked");
        }

        Appointment appointment = Appointment.builder()
                .appointmentTime(appointmentTime)
                .reasonForVisit(appointmentRequest.getReasonForVisit())
                .comments(appointmentRequest.getComments())
                .doctor(doctor)
                .patient(patient)
                .status(AppointmentStatus.PENDING)
                .build();

        return appointmentRepository.save(appointment);

    }

}
