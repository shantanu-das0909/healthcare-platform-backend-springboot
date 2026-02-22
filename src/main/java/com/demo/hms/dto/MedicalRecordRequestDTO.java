package com.demo.hms.dto;

import com.demo.hms.entity.Prescription;
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
