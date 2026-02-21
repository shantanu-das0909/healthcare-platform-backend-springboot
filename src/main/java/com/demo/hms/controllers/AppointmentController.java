package com.demo.hms.controllers;

import com.demo.hms.dto.NewAppointmentRequest;
import com.demo.hms.entity.Appointment;
import com.demo.hms.services.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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

        log.debug("Enter >> AppointmentController getDoctorAvailability() method ");
        List<LocalDateTime> availableSlots = appointmentService.getDoctorAvailability(doctorId, date);
        log.debug("Exit >> AppointmentController getDoctorAvailability() method ");
        return ResponseEntity.status(HttpStatus.OK).body(availableSlots);
    }

    // --- BOOK NEW APPOINTMENT ---
    // POST /api/appointments/book-appointment
    @PostMapping("/book-appointment")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody NewAppointmentRequest newAppointmentRequest) {
        Appointment savedAppointment = appointmentService.bookAppointment(newAppointmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    // --- CANCEL APPOINTMENT ---
    // PUT /api/appointments/change-status/1?status=cancel
    @PutMapping("/change-status/{appointmentId}")
    public ResponseEntity<Appointment> changeAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam String status) {

        Appointment updateAppointment = appointmentService.changeStatus(appointmentId, status);
        return ResponseEntity.ok(updateAppointment);
    }
}
