package com.hospital.app.controllerTest;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.commands.AddScheduleCommand;
import com.hospital.app.dto.ScheduleDto;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Patient;
import com.hospital.app.service.Service;
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
public class AddScheduleCommandTest {
    @Mock
    Service<Doctor> doctorService;
    @Mock
    Service<Patient> patientService;
    @Mock
    Service<ScheduleDto> scheduleService;
    @InjectMocks
    AddScheduleCommand cpc =new AddScheduleCommand();

    @Test
    void getExecuteCreateNullDoctorNullSubmit() throws DBException, SQLException, ParseException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter(Mockito.any())).thenReturn(null);
        when(request.getParameter(ControllerConstants.PATIENT_ID)).thenReturn("5");
        when(request.getAttribute("schedule")).thenReturn(null);
        when(request.getParameter("from")).thenReturn("medic");
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals(ControllerConstants.PAGE_ADD_SCHEDULE, cpc.execute(request, MessageManager.EN));
    }

    @Test
    void getExecuteCreateNullDoctorSubmit() throws DBException, SQLException, ParseException, ValidateException {
        MessageManager currentMessageLocale =MessageManager.EN;
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter(Mockito.any())).thenReturn(null);
        when(request.getParameter(ControllerConstants.PATIENT_ID)).thenReturn("5");
        when(request.getParameter("from")).thenReturn("medic");
        when(request.getParameter(ControllerConstants.SUBMIT)).thenReturn("submit");
        when(request.getAttribute("schedule")).thenReturn(null);
        when(scheduleService.create(Mockito.any())).thenThrow(new ValidateException("Doctor"));
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals(ControllerConstants.PAGE_ADD_SCHEDULE, cpc.execute(request, currentMessageLocale));
    }


}
