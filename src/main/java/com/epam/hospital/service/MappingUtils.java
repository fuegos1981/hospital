package com.epam.hospital.service;

import com.epam.hospital.dto.AppointmentDto;
import com.epam.hospital.dto.ScheduleDto;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Diagnosis;
import com.epam.hospital.model.Schedule;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.SimpleService;

import java.sql.SQLException;

public class MappingUtils {

    //из entity в dto
    public ScheduleDto mapToScheduleDto(Schedule entity){
        ScheduleDto dto = new ScheduleDto();
        dto.setId(entity.getId());
        dto.setDoctorId(entity.getDoctor().getId());
        dto.setPatientId(entity.getPatient().getId());
        dto.setDoctorName(entity.getDoctor().getLastName()+" "+entity.getDoctor().getFirstName());
        dto.setPatientName(entity.getPatient().toString());
        dto.setDateVisit(entity.getDateVisit());

        return dto;
    }
    //из dto в entity
    public Schedule mapToSchedule(ScheduleDto dto) throws DBException, ValidateException, SQLException {
        Schedule entity = new Schedule();
        entity.setId(dto.getId());
        entity.setDoctor(DoctorService.getDoctorService().readById(dto.getDoctorId()));
        entity.setPatient(PatientService.getPatientService().readById(dto.getPatientId()));
        entity.setDateVisit(dto.getDateVisit());
        return entity;
    }


    //из entity в dto
    public AppointmentDto mapToAppointmentDto(Appointment entity){
        AppointmentDto dto = new AppointmentDto();
        dto.setId(entity.getId());
        dto.setDoctorId(entity.getDoctor().getId());
        dto.setPatientId(entity.getPatient().getId());
        dto.setDiagnosisId(entity.getDiagnosis().getId());
        dto.setDiagnosisName(entity.getDiagnosis().getName());
        dto.setDoctorName(entity.getDoctor().getLastName()+" "+entity.getDoctor().getFirstName());
        dto.setPatientName(entity.getPatient().toString());
        dto.setCategoryName(entity.getDoctor().getCategory().getName());
        dto.setDateCreate(entity.getDateCreate());
        dto.setMedication(entity.getMedication());
        dto.setProcedure(entity.getProcedure());
        dto.setOperation(entity.getOperation());
        return dto;
    }
    //из dto в entity
    public Appointment mapToAppointment(AppointmentDto dto) throws DBException, ValidateException, SQLException {
        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setDoctor(DoctorService.getDoctorService().readById(dto.getDoctorId()));
        entity.setPatient(PatientService.getPatientService().readById(dto.getPatientId()));
        entity.setDiagnosis((Diagnosis) SimpleService.getSimpleService("Diagnosis").readById(dto.getDiagnosisId()));
        entity.setDateCreate(dto.getDateCreate());
        entity.setMedication(dto.getMedication());
        entity.setProcedure(dto.getProcedure());
        entity.setOperation(dto.getOperation());

        return entity;
    }
}
