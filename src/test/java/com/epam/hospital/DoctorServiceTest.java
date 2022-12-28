package com.epam.hospital;

import com.epam.hospital.model.Category;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.ValidateException;
import org.junit.jupiter.api.Test;

import javax.print.Doc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoctorServiceTest {

    @Test
    void testBadInputLastNameTestingCreate() {
        Doctor doctor = Doctor.createDoctor("1At","Anna",Category.createInstance("Pediatr"));
        Exception exception = assertThrows(ValidateException.class, () -> {
            DoctorService.getDoctorService().create(doctor);
        });
        assertTrue(exception.getMessage().contains("last_name"));
    }

    @Test
    void testBadInputFirstNameTestingCreate() {

        Doctor doctor = Doctor.createDoctor("Last","",Category.createInstance("Pediatr"));
        Exception exception = assertThrows(ValidateException.class, () -> {
            DoctorService.getDoctorService().create(doctor);
        });
        assertTrue(exception.getMessage().contains("first_name"));
    }
    @Test
    void testBadInputLoginTestingCreate() {

        Doctor doctor = Doctor.createDoctor("Last","Anna",Category.createInstance("Pediatr"));
        Exception exception = assertThrows(ValidateException.class, () -> {
            DoctorService.getDoctorService().create(doctor);
        });
        assertTrue(exception.getMessage().contains("login"));
    }
    @Test
    void testNullDoctorReadByIdTesting() {

        Exception exception = assertThrows(ValidateException.class, () -> {
            DoctorService.getDoctorService().readById(null);
        });
        assertTrue(exception.getMessage().equals("doctor"));
    }

}