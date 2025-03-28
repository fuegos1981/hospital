package com.hospital.app.controllerTest;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.commands.CreateAppointmentCommand;
import com.hospital.app.dto.AppointmentDto;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.model.Role;
import com.hospital.app.repository.Fields;
import com.hospital.app.service.Service;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAppointmentCommandTest {
    @Mock
    Service<AppointmentDto> appointmentService;
    @InjectMocks
    CreateAppointmentCommand cpc =new CreateAppointmentCommand();

    private static HttpServletRequest request;
    private static HttpSession session;
    @BeforeAll
    static void testInit(){
        request= mock(HttpServletRequest.class);
        session= mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void getExecuteCreateNullAppointmentNullSubmit() throws DBException, SQLException, ParseException {
        when(request.getAttribute("appointment")).thenReturn(null);
        when(request.getParameter("from")).thenReturn("medic");
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals(ControllerConstants.PAGE_EDIT_APPOINTMENT, cpc.execute(request, MessageManager.EN));
    }

    @Test
    void getExecuteCreateNullPatientSubmit() throws DBException, SQLException, ParseException, ValidateException {
        MessageManager currentMessageLocale =MessageManager.EN;
        when(request.getParameter(Mockito.any())).thenReturn(null);
        when(request.getParameter(Fields.DATE_CREATE)).thenReturn("2022-12-12");
        when(request.getParameter(ControllerConstants.DIAGNOSIS_ID)).thenReturn("5");
        when(request.getParameter(ControllerConstants.DOCTOR_ID)).thenReturn("5");
        when(request.getParameter(ControllerConstants.PATIENT_ID)).thenReturn(null);
        when(request.getParameter("from")).thenReturn("medic");
        when((Role) session.getAttribute("role")).thenReturn(Role.ADMIN);
        when(request.getParameter(ControllerConstants.SUBMIT)).thenReturn("submit");
       // when(request.getAttribute("appointment")).thenReturn(app);
        lenient().when(appointmentService.create(Mockito.any())).thenThrow(new ValidateException("Patient"));
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals(ControllerConstants.PAGE_EDIT_APPOINTMENT, cpc.execute(request, currentMessageLocale));
    }
}
