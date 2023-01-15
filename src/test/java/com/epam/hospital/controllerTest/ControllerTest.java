package com.epam.hospital.controllerTest;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.Controller;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTest {
    @Test
    void testGetCurrentLocale() throws NoSuchFieldException, IllegalAccessException {
        HttpServletRequest request= mock(HttpServletRequest.class);
        HttpSession session= mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);
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
        HttpServletRequest request= mock(HttpServletRequest.class);
        HttpSession session= mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);
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
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter("id")).thenReturn("5");
        assertEquals(ControllerUtils.parseID(request,"id"),5);
    }

    @Test
    void testParseIDNull(){
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter("id")).thenReturn(null);
        assertNull(ControllerUtils.parseID(request,"id"));
    }


}
