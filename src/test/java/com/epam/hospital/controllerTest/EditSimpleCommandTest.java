package com.epam.hospital.controllerTest;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.commands.EditSimpleCommand;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.impl.SimpleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditSimpleCommandTest {

    @Mock
    SimpleService simpleService;

    @InjectMocks
    EditSimpleCommand esc =new EditSimpleCommand();

    private static HttpServletRequest request;
    @BeforeAll
    static void testInit(){
        request= mock(HttpServletRequest.class);
    }

    @Test
    public void getExecuteCreateNullSimpleNullSubmit() throws DBException{
        when(request.getParameter(Mockito.any())).thenReturn(null);
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals(ControllerConstants.PAGE_EDIT_SIMPLE, esc.execute(request, MessageManager.EN));
    }

    @Test
    public void getExecuteCreateSimpleSubmit() throws DBException, ValidateException {
        when(request.getParameter(ControllerConstants.NAME)).thenReturn(Constants.DIAGNOSIS);
        when(request.getParameter(ControllerConstants.SIMPLE)).thenReturn("Acne");
        when(request.getParameter(Fields.ID)).thenReturn(null);
        when(request.getParameter("submit")).thenReturn("submit");
        when(simpleService.create(Mockito.any())).thenReturn(true);
        Mockito.doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals("/hospital/Simple?command=simple", esc.execute(request, MessageManager.EN));
    }
}
