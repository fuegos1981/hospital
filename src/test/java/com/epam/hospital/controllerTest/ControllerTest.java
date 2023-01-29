package com.epam.hospital.controllerTest;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.Controller;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.repository.Fields;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControllerTest {
    private static HttpServletRequest request;
    private static HttpSession session;

    @BeforeAll
    static void testInit(){
        request= mock(HttpServletRequest.class);
        session= mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);
    }

    @Test
    void testGetCurrentLocale() throws NoSuchFieldException, IllegalAccessException {
        when(request.getParameter(ControllerConstants.SUBMIT_US)).thenReturn(null);
        Mockito.lenient().doNothing().when(session).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        Controller controller = new Controller();
        controller.getCurrentLocale(request);
        Field privateStringField = Controller.class.getDeclaredField("currentMessageLocale");
        privateStringField.setAccessible(true);
        MessageManager fieldValue = (MessageManager) privateStringField.get(controller);
        assertSame(MessageManager.EN, fieldValue);
    }

    @Test
    void testGetCurrentLocaleUA() throws NoSuchFieldException, IllegalAccessException {
        when(session.getAttribute(ControllerConstants.LOCALE)).thenReturn(ControllerConstants.LOCALE_UA);
        Mockito.lenient().doNothing().when(session).setAttribute(Mockito.isA(String.class), Mockito.isA(Object.class));
        Controller controller = new Controller();
        controller.getCurrentLocale(request);
        Field privateStringField = Controller.class.getDeclaredField("currentMessageLocale");
        privateStringField.setAccessible(true);
        MessageManager fieldValue = (MessageManager) privateStringField.get(controller);
        assertSame(MessageManager.UA, fieldValue);
    }

    @Test
    void testParseID(){
        when(request.getParameter(Fields.ID)).thenReturn("5");
        assertEquals(ControllerUtils.parseID(request,Fields.ID),5);
    }

    @Test
    void testParseIDNull(){
        when(request.getParameter(Fields.ID)).thenReturn(null);
        assertNull(ControllerUtils.parseID(request,Fields.ID));
    }

    @Test
    void testSetPathReturnMedic(){
        when(request.getParameter("path_return")).thenReturn(null);
        when(request.getParameter("from")).thenReturn("medic");
        when(request.getParameter(ControllerConstants.PATIENT_ID)).thenReturn("5");
        when(request.getParameter(ControllerConstants.DOCTOR_ID)).thenReturn(null);
        assertEquals(ControllerUtils.setPathReturn(request),"/hospital/medic?command=medic&patient_id=5");
    }

    @Test
    void testSetPathReturnAdmin(){
        when(request.getParameter("path_return")).thenReturn(null);
        when(request.getParameter("from")).thenReturn("admin");
        when(request.getParameter(ControllerConstants.PATIENT_ID)).thenReturn("5");
        when(request.getParameter(ControllerConstants.DOCTOR_ID)).thenReturn(null);
        assertEquals(ControllerUtils.setPathReturn(request),"/hospital/admin?command=admin");
    }

    @Test
    void testIsDownLoadNull() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HttpServletResponse response= mock(HttpServletResponse.class);
        when(request.getParameter("download")).thenReturn(null);
        Method privateMethod = Controller.class.getDeclaredMethod("isDownLoad", HttpServletRequest.class, HttpServletResponse.class);
        privateMethod.setAccessible(true);
        assertFalse((Boolean) privateMethod.invoke(new Controller(),request, response));
    }


}
