package com.demo.hms.controllers;

import com.demo.hms.dto.NewAppointmentRequest;
import com.demo.hms.entity.Appointment;
import com.demo.hms.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // --- VIEW DOCTOR AVAILABILITY ---
    // GET /api/appointments/availability?doctorId=1&date=2025-12-10
    @GetMapping("/availability")
    public ResponseEntity<List<LocalDateTime>> getDoctorAvailability(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<LocalDateTime> availableSlots = appointmentService.getDoctorAvailability(doctorId, date);
        return ResponseEntity.status(HttpStatus.OK).body(availableSlots);
    }

    // --- BOOK NEW APPOINTMENT ---
    // POST /api/appointments/book-appointment
    @PostMapping("/book-appointment")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody NewAppointmentRequest newAppointmentRequest) {
        Appointment savedAppointment = appointmentService.bookAppointment(newAppointmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }
}
