package com.demo.hms.modules.appointment.dto;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecordRequestDTO {

    private long appointmentId;

    private String reasonForVisit;

    private String clinicalNotes;

    private String diagnosis;

    private List<PrescriptionDTO> prescriptions;
}
