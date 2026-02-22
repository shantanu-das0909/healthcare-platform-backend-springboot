package com.demo.hms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime visitDate;

    private String reasonForVisit;

    private String clinicalNotes;

    private String diagnosis;

    @OneToMany(mappedBy = "medicalRecord")
    @JsonManagedReference // The "forward" part: serialized normally
    private List<Prescription> prescriptions;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    public void addPrescription(Prescription prescription){
        this.prescriptions.add(prescription);
        prescription.setMedicalRecord(this);
    }

    public void removePrescription(Prescription prescription) {
        prescriptions.remove(prescription);
        prescription.setMedicalRecord(null);
    }
}
