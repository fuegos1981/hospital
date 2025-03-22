package com.hospital.app.serviceTest;

import com.hospital.app.dto.AppointmentDto;

import com.hospital.app.service.impl.AppointmentService;
import com.hospital.app.exceptions.ValidateException;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceTest {


    @Test
    void testNullDoctorTestingCreate(){
        AppointmentDto app = AppointmentDto.createAppointmentDto(new Date(),1,1,null);
        Exception exception = assertThrows(ValidateException.class, () -> AppointmentService.getAppointmentService().create(app));
        assertTrue(exception.getMessage().contains("doctor"));
    }

    @Test
    void testNullPatientTestingCreate() {
        AppointmentDto app = AppointmentDto.createAppointmentDto(new Date(),1,null,1);
        Exception exception = assertThrows(ValidateException.class, () -> AppointmentService.getAppointmentService().create(app));
        assertTrue(exception.getMessage().contains("patient"));
    }

    @Test
    void testNullDiagnosisTestingCreate() {
        AppointmentDto app = AppointmentDto.createAppointmentDto(new Date(),null,1,1);
        Exception exception = assertThrows(ValidateException.class, () -> AppointmentService.getAppointmentService().create(app));
        assertTrue(exception.getMessage().contains("diagnosis"));
    }

    @Test
    void EmptyDescriptionTestingCreate() {
        AppointmentDto app = AppointmentDto.createAppointmentDto(new Date(),1,1,1);
        Exception exception = assertThrows(ValidateException.class, () -> AppointmentService.getAppointmentService().create(app));
        assertTrue(exception.getMessage().contains("description"));
    }

    @Test
    void testNullAppointmentReadByIdTesting() {
        Exception exception = assertThrows(ValidateException.class, () -> AppointmentService.getAppointmentService().readById(null));
        assertEquals(exception.getMessage(),"appointment");
    }

}
