package com.hospital.app.serviceTest;

import com.hospital.app.dto.ScheduleDto;
import com.hospital.app.service.impl.ScheduleService;
import com.hospital.app.exceptions.ValidateException;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleServiceTest {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Test
    void testNullDoctorTestingCreate() throws ParseException {
        ScheduleDto schedule = ScheduleDto.createScheduleDto(null,2,
                formatter.parse("2023-01-05 12:00"));
        Exception exception = assertThrows(ValidateException.class,
                () -> ScheduleService.getScheduleService().create(schedule));
        assertTrue(exception.getMessage().contains("doctor"));
    }

    @Test
    void testNullPatientTestingCreate() throws ParseException {
        ScheduleDto schedule = ScheduleDto.createScheduleDto(3,null,formatter.parse("2023-01-05 12:00"));
        Exception exception = assertThrows(ValidateException.class, () -> ScheduleService.getScheduleService().create(schedule));
        assertTrue(exception.getMessage().contains("patient"));
    }
    @Test
    void testNullDataVisitTestingCreate() {
        ScheduleDto schedule = ScheduleDto.createScheduleDto(3,2,null);
        Exception exception = assertThrows(ValidateException.class, () -> ScheduleService.getScheduleService().create(schedule));
        assertTrue(exception.getMessage().contains("visit_time"));
    }

    @Test
    void testBeforeNowDataVisitTestingCreate() throws ParseException {
        ScheduleDto schedule = ScheduleDto.createScheduleDto(3,2,formatter.parse("2022-01-05 12:00"));
        Exception exception = assertThrows(ValidateException.class, () -> ScheduleService.getScheduleService().create(schedule));
        assertTrue(exception.getMessage().contains("visit_time"));
    }

}
