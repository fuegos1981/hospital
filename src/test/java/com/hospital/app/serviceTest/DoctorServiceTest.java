package com.hospital.app.serviceTest;

import com.hospital.app.model.Category;
import com.hospital.app.model.Doctor;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.model.SimpleModel;
import com.hospital.app.service.impl.DoctorService;
import com.hospital.app.exceptions.ValidateException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorServiceTest {
    private static Category category;

    @BeforeAll
    static void testInit(){
        category = (Category) SimpleModel.getSimpleInstance("Category");
        category.setName("Pediatric");
    }

    @Test
    void testBadInputLastNameTestingCreate() {
        Doctor doctor = Doctor.createDoctor("1At","Anna",category);
        Exception exception = assertThrows(ValidateException.class, () -> DoctorService.getDoctorService().create(doctor));
        assertTrue(exception.getMessage().contains("last_name"));
    }

    @Test
    void testBadInputFirstNameTestingCreate() {
        Doctor doctor = Doctor.createDoctor("Last","",category);
        Exception exception = assertThrows(ValidateException.class, () -> DoctorService.getDoctorService().create(doctor));
        assertTrue(exception.getMessage().contains("first_name"));
    }

    @Test
    void testBadInputLoginTestingCreate() {
        Doctor doctor = Doctor.createDoctor("Last","Anna",category);
        Exception exception = assertThrows(ValidateException.class, () -> DoctorService.getDoctorService().create(doctor));
        assertTrue(exception.getMessage().contains("login"));
    }

    @Test
    void testNullDoctorReadByIdTesting() throws DBException, ValidateException, SQLException {
        assertNull(DoctorService.getDoctorService().readById(null));
    }

}
