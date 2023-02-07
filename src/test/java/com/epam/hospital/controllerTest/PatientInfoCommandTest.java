package com.epam.hospital.controllerTest;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.commands.PatientInfoCommand;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientInfoCommandTest {
    private static HttpServletRequest request;

    @Mock
    Service<Patient> patientService;

    @InjectMocks
    PatientInfoCommand cpc =new PatientInfoCommand();

    @BeforeAll
    static void testInit(){
        request= mock(HttpServletRequest.class);
    }

    @Test
    void getExecuteNotRealPatient() throws DBException, SQLException, ParseException, ValidateException {
        when(request.getParameter(Fields.ID)).thenReturn("1000");
        when(patientService.readById(1000)).thenThrow(new ValidateException("patient"));
        assertEquals(ControllerConstants.PAGE_ERROR, cpc.execute(request, MessageManager.EN));
    }
    @Test
    void getExecutePatient() throws DBException, SQLException, ParseException {
        when(request.getAttribute(Fields.PATIENT_ID)).thenReturn("1");
        assertEquals(ControllerConstants.PAGE_PATIENT_INFO, cpc.execute(request, MessageManager.EN));
    }
}
