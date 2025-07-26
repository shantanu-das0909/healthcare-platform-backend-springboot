package com.demo.hms.controllers;

import com.demo.hms.dto.AddDoctorRequest;
import com.demo.hms.dto.UpdateAppointmentRequest;
import com.demo.hms.entity.Appointment;
import com.demo.hms.entity.Doctor;
import com.demo.hms.entity.Patient;
import com.demo.hms.services.AdminService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-doctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody AddDoctorRequest addDoctorRequest) {

        return ResponseEntity.ok(adminService.addDoctor((addDoctorRequest)));
    }

    @DeleteMapping("/delete-doctor/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long doctorId) {
        adminService.deleteDoctor(doctorId);
        return new ResponseEntity<>("Successfully deleted doctor with id " + doctorId, HttpStatus.OK);
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getPatients() {
        return ResponseEntity.ok(adminService.getAllPatients());
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAppointments() {
        return ResponseEntity.ok(adminService.getAllAppointments());
    }

    @GetMapping("/appointments/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForAPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(adminService.getAllAppointmentsForAPatient(patientId));
    }

    @DeleteMapping("/patients/{patientId}")
    public ResponseEntity<Boolean> deletePatient(@PathVariable Long patientId) {
        adminService.deletePatient(patientId);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<String> updateAppointment(
            @RequestBody UpdateAppointmentRequest updateAppointmentRequest,
            @PathVariable Long appointmentId
    ) {
        adminService.updateAppointment(updateAppointmentRequest, appointmentId);
        return ResponseEntity.ok("Appointment update successfully");
    }

}
