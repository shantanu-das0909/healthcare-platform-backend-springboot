package com.demo.hms.modules.patient.entity;

import com.demo.hms.modules.appointment.entity.MedicalRecord;
import com.demo.hms.modules.appointment.entity.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @BatchSize(size = 20) // Optimization fallback
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    @ToString.Exclude
    private List<MedicalRecord> medicalRecords;

}
