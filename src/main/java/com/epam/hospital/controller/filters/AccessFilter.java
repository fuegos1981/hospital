package com.epam.hospital.controller.filters;

import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = { "/*" })
public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Role role = (Role) session.getAttribute(ControllerConstants.ROLE);
        if (role == null&&!req.getServletPath().equals("/login")) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher(ControllerConstants.PAGE_INDEX);
            dispatcher.forward(req, resp);
            return;
        }
        else if(role==Role.DOCTOR&&req.getServletPath().equals("/admin")){
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher(ControllerConstants.PAGE_MEDIC);
            dispatcher.forward(req, resp);
            return;

           // resp.sendRedirect("/hospital/medic?command=medic");
            //return;
        }
        // pass the request along the filter chain
        chain.doFilter(request, response);
    }
}
