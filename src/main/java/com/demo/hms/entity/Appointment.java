package com.demo.hms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    private LocalDateTime appointmentDate;

    private String appointmentReason;

    private String comments;

    private String status;

    private String cancelReason;

    @ManyToOne
    @JoinColumn(name = "doctorId", referencedColumnName = "doctorId")
//    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientId", nullable = false)
//    @JsonIgnore
    private Patient patient;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
