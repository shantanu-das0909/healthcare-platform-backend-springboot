package com.demo.hms.dto;

import lombok.Data;

@Data
public class PrescriptionDTO {
    private Long id;

    private String medicationName;

    private String dosage;

    private String frequency;

    private String duration;

    private String instruction;
}
