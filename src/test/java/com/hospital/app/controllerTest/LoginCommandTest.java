package com.hospital.app.controllerTest;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.commands.LoginCommand;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.model.Category;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Role;
import com.hospital.app.model.SimpleModel;
import com.hospital.app.service.impl.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginCommandTest {
    @Mock
    DoctorService doctorService;
    @InjectMocks
    LoginCommand lc=new LoginCommand();

    @Test
    public void getExecuteNullLoginNotNullSubmit() throws DBException, ValidateException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter("submit")).thenReturn("submit");
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("111");
        when(doctorService.readByLoginPassword(request.getParameter("username"), request.getParameter("password"))).thenThrow(ValidateException.class);
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        Assertions.assertEquals(ControllerConstants.PAGE_INDEX, lc.execute(request, MessageManager.EN));
    }
    @Test
    public void getExecuteIsUserSubmit() throws DBException, ValidateException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        HttpSession session= mock(HttpSession.class);
        Category category = (Category) SimpleModel.getSimpleInstance("Category");
        category.setName("Pediatric");
        category.setId(5);
        when(request.getSession(true)).thenReturn(session);
        when(request.getParameter("submit")).thenReturn("submit");
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("111");
        Doctor doctor = Doctor.createDoctor("Vanow","Oleg", category);
        doctor.setRole(Role.ADMIN);
        when(doctorService.readByLoginPassword(request.getParameter("username"),
                request.getParameter("password"))).thenReturn(doctor);
        Mockito.lenient().doNothing().when(request).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        assertEquals("/hospital/admin?command=admin", lc.execute(request, MessageManager.EN));
    }

}
