package com.epam.hospital.controllerTest;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.commands.CreateDoctorCommand;
import com.epam.hospital.model.Category;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.SimpleService;
import com.epam.hospital.exceptions.ValidateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateDoctorCommandTest {

    @Mock
    SimpleService categoryService;
    @Mock
    Service<Doctor> doctorService;
    @InjectMocks
    CreateDoctorCommand cpc =new CreateDoctorCommand();

    @Test
    void getExecuteCreateNullDoctorNullSubmit() throws DBException, SQLException, ParseException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter(Mockito.any())).thenReturn(null);
        when(request.getAttribute("role")).thenReturn("DOCTOR");
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals(ControllerConstants.PAGE_EDIT_DOCTOR, new CreateDoctorCommand().execute(request, MessageManager.EN));
    }

    @Test
    void getExecuteCreateNullDoctorNotNullSubmit() throws DBException, SQLException, ParseException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter("submit")).thenReturn("submit");
        when(request.getAttribute("role")).thenReturn("DOCTOR");
        when(request.getParameter(Mockito.argThat(arg->!arg.equals("submit")))).thenReturn(null);
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals(ControllerConstants.PAGE_EDIT_DOCTOR, new CreateDoctorCommand().execute(request, MessageManager.EN));
    }

    @Test
    void getExecuteCreateDoctorSubmit() throws DBException, SQLException, ParseException, ValidateException {
        Doctor doctor = Doctor.createDoctor("Markov","Ivan", Category.createInstance("Anesthesiologist"));
        HttpServletRequest request= mock(HttpServletRequest.class);
        lenient().when(request.getParameter("LAST_NAME")).thenReturn("Markov1");
        lenient().when(request.getParameter("gender")).thenReturn(null);
        lenient().when(request.getParameter(Fields.FIRST_NAME)).thenReturn("Ivan");
        lenient().when(request.getParameter("submit")).thenReturn("submit");
        lenient().when(request.getParameter("login")).thenReturn("MarkovI");
        lenient().when(request.getParameter("password")).thenReturn("111");
        lenient().when(request.getParameter(ControllerConstants.CATEGORY_ID)).thenReturn("5");
        lenient().when(request.getAttribute("role")).thenReturn("DOCTOR");
        Category category = Category.createInstance("Anesthesiologist");
        category.setId(5);
        lenient().when((Category)categoryService.readById(Mockito.any())).thenReturn(category);
        when(doctorService.create(Mockito.any())).thenReturn(true);
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals("/hospital/main?command=admin", cpc.execute(request, MessageManager.EN));
    }
}
