package com.hospital.app.controllerTest;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.commands.CreateDoctorCommand;
import com.hospital.app.model.Category;
import com.hospital.app.model.Doctor;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.model.SimpleModel;
import com.hospital.app.repository.Fields;
import com.hospital.app.service.Service;
import com.hospital.app.service.impl.SimpleService;
import com.hospital.app.exceptions.ValidateException;
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
        Category category = (Category) SimpleModel.getSimpleInstance("Category");
        category.setName("Anesthesiologist");
        category.setId(5);
        //Doctor doctor = Doctor.createDoctor("Markov","Ivan", category);
        HttpServletRequest request= mock(HttpServletRequest.class);
        lenient().when(request.getParameter("LAST_NAME")).thenReturn("Markov1");
        lenient().when(request.getParameter("gender")).thenReturn(null);
        lenient().when(request.getParameter(Fields.FIRST_NAME)).thenReturn("Ivan");
        lenient().when(request.getParameter("submit")).thenReturn("submit");
        lenient().when(request.getParameter("login")).thenReturn("MarkovI");
        lenient().when(request.getParameter("password")).thenReturn("111");
        lenient().when(request.getParameter(ControllerConstants.CATEGORY_ID)).thenReturn("5");
        lenient().when(request.getAttribute("role")).thenReturn("DOCTOR");

        lenient().when((Category)categoryService.readById(5)).thenReturn(category);
        when(doctorService.create(Mockito.any())).thenReturn(true);
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals("/hospital/main?command=admin", cpc.execute(request, MessageManager.EN));
    }
}
