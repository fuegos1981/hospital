package com.hospital.app.controllerTest;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.commands.CreatePatientCommand;
import com.hospital.app.model.Patient;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.Fields;
import com.hospital.app.service.Service;
import com.hospital.app.exceptions.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.text.ParseException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatePatientCommandTest {

    @Mock
    Service<Patient> patientService;

    @InjectMocks
    CreatePatientCommand cpc =new CreatePatientCommand();

    @Test
    public void getExecuteCreateNullPatientNullSubmit() throws DBException, SQLException, ParseException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter(Mockito.any())).thenReturn(null);
        when(request.getAttribute("gender")).thenReturn("MALE");
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        Assertions.assertEquals(ControllerConstants.PAGE_EDIT_PATIENT, new CreatePatientCommand().execute(request, MessageManager.EN));
    }

    @Test
    public void getExecuteCreateNullPatientNotNullSubmit() throws DBException, SQLException, ParseException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter("submit")).thenReturn("submit");
        when(request.getAttribute("gender")).thenReturn("MALE");
        when(request.getParameter(Mockito.argThat(arg->!arg.equals("submit")))).thenReturn(null);
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals(ControllerConstants.PAGE_EDIT_PATIENT, new CreatePatientCommand().execute(request, MessageManager.EN));
    }
    @Test
    public void getExecuteCreatePatientSubmit() throws DBException, SQLException, ParseException, ValidateException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        lenient().when(request.getParameter("LAST_NAME")).thenReturn("Nas1");
        lenient().when(request.getParameter("gender")).thenReturn(null);
        lenient().when(request.getParameter("id")).thenReturn(null);
        lenient().when(request.getParameter(Fields.FIRST_NAME)).thenReturn("Anna");
        lenient().when(request.getParameter(Fields.PATIENT_BIRTHDAY)).thenReturn("1981-10-15");
        lenient().when(request.getParameter(Fields.PATIENT_EMAIL)).thenReturn(null);
        when(request.getAttribute("gender")).thenReturn("FEMALE");
        lenient().when(request.getParameter("submit")).thenReturn("submit");
        lenient().when(patientService.create(Mockito.any())).thenReturn(true);
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals("/hospital/Admin?command=admin", cpc.execute(request, MessageManager.EN));
    }


}
