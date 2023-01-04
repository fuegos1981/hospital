package com.epam.hospital.serviceTest;

import com.epam.hospital.model.*;
import com.epam.hospital.service.impl.AppointmentService;
import com.epam.hospital.exceptions.ValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppointmentServiceTest {
    private  Patient patient;
    private  Doctor doctor;
    private  Diagnosis diagnosis;

    @BeforeEach
    void init() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        patient = Patient.createPatient("Nas","Anna",
                formatter.parse("12-12-1981"),"e@ed.com.ua", Gender.FEMALE);
        doctor = Doctor.createDoctor("Markov","Ivan",Category.createInstance("Anesthesiologist"));
        diagnosis= Diagnosis.createInstance("Acne");
    }

    @Test
    void testNullDoctorTestingCreate(){
        Appointment app = Appointment.createAppointment(new Date(),diagnosis,patient,null);
        Exception exception = assertThrows(ValidateException.class, () -> {
            AppointmentService.getAppointmentService().create(app);
        });
        assertTrue(exception.getMessage().contains("doctor"));
    }

    @Test
    void testNullPatientTestingCreate() {
        Appointment app = Appointment.createAppointment(new Date(),diagnosis,null,doctor);
        Exception exception = assertThrows(ValidateException.class, () -> {
            AppointmentService.getAppointmentService().create(app);
        });
        assertTrue(exception.getMessage().contains("patient"));
    }

    @Test
    void testNullDiagnosisTestingCreate() {
        Appointment app = Appointment.createAppointment(new Date(),null,patient,doctor);
        Exception exception = assertThrows(ValidateException.class, () -> {
            AppointmentService.getAppointmentService().create(app);
        });
        assertTrue(exception.getMessage().contains("diagnosis"));
    }

    @Test
    void EmptyDescriptionTestingCreate() {
        Appointment app = Appointment.createAppointment(new Date(),diagnosis,patient,doctor);
        Exception exception = assertThrows(ValidateException.class, () -> {
            AppointmentService.getAppointmentService().create(app);
        });
        assertTrue(exception.getMessage().contains("description"));
    }

    @Test
    void testNullAppointmentReadByIdTesting() {
        Exception exception = assertThrows(ValidateException.class, () -> {
            AppointmentService.getAppointmentService().readById(null);
        });
        assertTrue(exception.getMessage().equals("appointment"));
    }

}
