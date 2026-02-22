package com.demo.hms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicationName;

    private String dosage;

    private String frequency;

    private String duration;

    private String instruction;

    @ManyToOne
    @JoinColumn(name = "medical_record_id", nullable = false)
    @JsonBackReference // The "backward" part: omitted from JSON
    private MedicalRecord medicalRecord;
}
