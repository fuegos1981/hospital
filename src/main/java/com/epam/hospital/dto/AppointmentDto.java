package com.epam.hospital.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppointmentDto {
    private int id;
    private Date dateCreate;
    private Integer diagnosisId;
    private Integer patientId;
    private Integer doctorId;
    private String diagnosisName;
    private String patientName;
    private String doctorName;
    private String categoryName;
    private String medication;
    private String procedure;
    private String operation;


    public static AppointmentDto createAppointmentDto(Date dateCreate, Integer diagnosisId, Integer patientId, Integer doctorId) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setDateCreate(dateCreate);
        appointmentDto.setDiagnosisId(diagnosisId);
        appointmentDto.setPatientId(patientId);
        appointmentDto.setDoctorId(doctorId);

        return appointmentDto;
    }

    public String getDescription() {
        StringBuilder sb =new StringBuilder();
        if (medication!=null&&!medication.isEmpty()){
            sb.append(medication).append("; ");
        }
        if (procedure!=null&&!procedure.isEmpty()){
            sb.append(procedure).append("; ");
        }
        if (operation!=null&&!operation.isEmpty()){
            sb.append(operation).append("; ");
        }
        return sb.toString();
    }


}
