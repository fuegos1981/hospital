package com.hospital.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ScheduleDto {
    private int id;
    private Integer doctorId;
    private Integer patientId;
    private String doctorName;
    private String patientName;
    private Date dateVisit;


    public static ScheduleDto createScheduleDto(Integer doctorId, Integer patientId, Date dateVisit){
        ScheduleDto schedule = new ScheduleDto();
        schedule.doctorId=doctorId;
        schedule.patientId=patientId;
        schedule.dateVisit = dateVisit;
        return schedule;
    }

}
