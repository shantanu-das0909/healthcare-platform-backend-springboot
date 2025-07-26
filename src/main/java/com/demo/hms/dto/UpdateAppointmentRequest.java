package com.demo.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAppointmentRequest {
    private String status;
    private String cancelReason;
    private Long doctorId;
}
