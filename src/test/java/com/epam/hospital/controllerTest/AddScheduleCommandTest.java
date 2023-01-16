package com.epam.hospital.controllerTest;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.commands.AddScheduleCommand;
import com.epam.hospital.controller.commands.CreateDoctorCommand;
import com.epam.hospital.dto.ScheduleDto;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Role;
import com.epam.hospital.service.Service;
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
