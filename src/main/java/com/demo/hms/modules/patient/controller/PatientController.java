package com.demo.hms.modules.patient.controller;

import com.demo.hms.modules.patient.dto.AddPatientRequest;
import com.demo.hms.modules.appointment.dto.NewAppointmentRequest;
import com.demo.hms.modules.appointment.entity.Appointment;
import com.demo.hms.modules.patient.entity.Patient;
import com.demo.hms.modules.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/new-appointment/{patientId}")
    public ResponseEntity<Appointment> newAppointment(@RequestBody NewAppointmentRequest newAppointmentRequest,
                                                      @PathVariable Long patientId) {

        return ResponseEntity.ok(patientService.addNewAppointment(newAppointmentRequest, patientId));
    }

    @PostMapping("/add-patient")
    public ResponseEntity<Patient> addPatient(@RequestBody AddPatientRequest addPatientRequest) {
        return ResponseEntity.ok(patientService.addNewPatient(addPatientRequest));
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

}
