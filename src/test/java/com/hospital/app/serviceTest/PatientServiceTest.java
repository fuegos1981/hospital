package com.hospital.app.serviceTest;

import com.hospital.app.model.Gender;
import com.hospital.app.model.Patient;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.service.impl.PatientService;
import com.hospital.app.exceptions.ValidateException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientServiceTest {
    @Test
    void testBadInputLastNameTestingCreate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Patient patient = Patient.createPatient("1At","Anna",
                formatter.parse("12-12-2012"),"e@ed.com.ua", Gender.FEMALE);
        Exception exception = assertThrows(ValidateException.class, () -> {
            PatientService.getPatientService().create(patient);
        });
        assertTrue(exception.getMessage().contains("last_name"));
    }

    @Test
    void testBadInputFirstNameTestingCreate() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Patient patient = Patient.createPatient("Nas","anna",
                formatter.parse("12-12-2012"),"e@ed.com.ua", Gender.FEMALE);
        Exception exception = assertThrows(ValidateException.class, () -> {
            PatientService.getPatientService().create(patient);
        });
        assertTrue(exception.getMessage().contains("first_name"));
    }
    @Test
    void testBadInputBirthdayTestingCreate() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Patient patient = Patient.createPatient("Nas","Anna",
                formatter.parse("12-12-2081"),"e@ed.com.ua", Gender.FEMALE);
        Exception exception = assertThrows(ValidateException.class, () -> {
            PatientService.getPatientService().create(patient);
        });
        assertTrue(exception.getMessage().contains("birthday"));
    }
    @Test
    void testNullPatientReadByIdTesting() throws DBException, SQLException {

        assertTrue(PatientService.getPatientService().readById(null)==null);
    }

    @Test
    void testNullPatientUpdateTesting() {
        Exception exception = assertThrows(ValidateException.class, () -> {
            PatientService.getPatientService().update(null);
        });
        assertTrue(exception.getMessage().equals("patient"));
    }
}
