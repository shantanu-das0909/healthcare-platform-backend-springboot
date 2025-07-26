package com.demo.hms.controllers;

import com.demo.hms.dto.AddPatientRequest;
import com.demo.hms.dto.NewAppointmentRequest;
import com.demo.hms.entity.Appointment;
import com.demo.hms.entity.Patient;
import com.demo.hms.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

}
