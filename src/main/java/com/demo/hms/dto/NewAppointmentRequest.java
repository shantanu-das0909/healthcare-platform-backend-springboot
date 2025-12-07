package com.demo.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAppointmentRequest {

    private LocalDateTime appointmentTime;

    private String reasonForVisit;

    private String comments;

    private Long doctorId;

    private Long patientId;
}
