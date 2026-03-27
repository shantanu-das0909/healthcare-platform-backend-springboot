package com.demo.hms.modules.appointment.entity;

import com.demo.hms.modules.doctor.entity.Doctor;
import com.demo.hms.modules.patient.entity.Patient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    private LocalDateTime appointmentTime;

    private String reasonForVisit;

    private String comments;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String cancelReason;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctorId")
    @ToString.Exclude
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @ToString.Exclude
    private Patient patient;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
