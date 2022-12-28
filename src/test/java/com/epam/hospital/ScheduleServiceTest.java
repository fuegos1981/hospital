package com.epam.hospital;

import com.epam.hospital.model.*;
import com.epam.hospital.service.impl.ScheduleService;
import com.epam.hospital.service.impl.ValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleServiceTest {
    private  Patient patient;
    private  Doctor doctor;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @BeforeEach
    void init() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        patient = Patient.createPatient("Nas","Anna",
                sf.parse("12-12-1981"),"e@ed.com.ua", Gender.FEMALE);
        doctor = Doctor.createDoctor("Markov","Ivan", Category.createInstance("Anesthesiologist"));
    }

    @Test
    void testNullDoctorTestingCreate() throws ParseException {
        Schedule schedule = Schedule.createSchedule(null,patient,formatter.parse("2023-01-05 12:00"));
        Exception exception = assertThrows(ValidateException.class, () -> {
            ScheduleService.getScheduleService().create(schedule);
        });
        assertTrue(exception.getMessage().contains("doctor"));
    }

    @Test
    void testNullPatientTestingCreate() throws ParseException {
        Schedule schedule = Schedule.createSchedule(doctor,null,formatter.parse("2023-01-05 12:00"));
        Exception exception = assertThrows(ValidateException.class, () -> {
            ScheduleService.getScheduleService().create(schedule);
        });
        assertTrue(exception.getMessage().contains("patient"));
    }
    @Test
    void testNullDataVisitTestingCreate() throws ParseException {
        Schedule schedule = Schedule.createSchedule(doctor,patient,null);
        Exception exception = assertThrows(ValidateException.class, () -> {
            ScheduleService.getScheduleService().create(schedule);
        });
        assertTrue(exception.getMessage().contains("visit_time"));
    }

    @Test
    void testBeforeNowDataVisitTestingCreate() throws ParseException {
        Schedule schedule = Schedule.createSchedule(doctor,patient,formatter.parse("2022-01-05 12:00"));
        Exception exception = assertThrows(ValidateException.class, () -> {
            ScheduleService.getScheduleService().create(schedule);
        });
        assertTrue(exception.getMessage().contains("visit_time"));
    }
}
