package com.demo.hms.modules.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
