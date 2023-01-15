package com.epam.hospital.controllerTest;

import com.epam.hospital.controller.ActionFactory;
import com.epam.hospital.controller.commands.AdminCommand;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionFactoryTest {
    @Test
    void testDefineCommandNull() {
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn(null);
        assertNull(new ActionFactory().defineCommand(request));
    }
    @Test
    void testDefineCommandAdmin() {
        HttpServletRequest request= mock(HttpServletRequest.class);
        when(request.getParameter("command")).thenReturn("admin");
        assertSame(new ActionFactory().defineCommand(request).getClass(), AdminCommand.class);
    }
}
