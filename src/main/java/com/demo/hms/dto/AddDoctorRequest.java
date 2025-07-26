package com.demo.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddDoctorRequest {
    private String name;
    private String email;
    private String phone;
    private String speciality;
}
