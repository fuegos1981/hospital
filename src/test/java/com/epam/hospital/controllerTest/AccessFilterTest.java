package com.epam.hospital.controllerTest;

import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.filters.AccessFilter;
import com.epam.hospital.model.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessFilterTest {
    private static HttpServletRequest request;
    private static HttpSession session;
    private static Method privateMethod;

    @BeforeAll
    static void testInit() throws NoSuchMethodException {
        request= mock(HttpServletRequest.class);
        session= mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        privateMethod = AccessFilter.class.getDeclaredMethod("getPath", HttpServletRequest.class);
        privateMethod.setAccessible(true);
    }

    @Test
    void testGetPathNull() throws InvocationTargetException, IllegalAccessException {
        when(request.getServletPath()).thenReturn("/admin");
        when(session.getAttribute(ControllerConstants.ROLE)).thenReturn(null);
        assertEquals(privateMethod.invoke(new AccessFilter(),(request)), "/hospital/login?command=login");
    }


    @Test
    void testGetPathDoctor() throws InvocationTargetException, IllegalAccessException {
        when(request.getServletPath()).thenReturn("/admin");
        when(session.getAttribute(ControllerConstants.ROLE)).thenReturn(Role.DOCTOR);
        when(session.getAttribute("user_id")).thenReturn(4);
        assertEquals(privateMethod.invoke(new AccessFilter(),request), "/hospital/medic?command=medic&doctor_id=4");
    }

@Test
    void testGetPathNurse() throws InvocationTargetException, IllegalAccessException {
        when(request.getServletPath()).thenReturn("/editDoctor");
        when(session.getAttribute("user_id")).thenReturn(5);
        when(session.getAttribute(ControllerConstants.ROLE)).thenReturn(Role.NURSE);
        assertEquals(privateMethod.invoke(new AccessFilter(),(request)), "/hospital/medic?command=medic&doctor_id=5");
    }
}
