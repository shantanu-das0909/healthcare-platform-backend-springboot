package com.demo.hms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String name;

    private String email;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalRecords;

}
