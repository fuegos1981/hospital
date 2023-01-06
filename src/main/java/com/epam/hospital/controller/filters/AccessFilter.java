package com.epam.hospital.controller.filters;

import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The class restricts access to users with different rights
 *Please see the {@link javax.servlet.annotation.WebFilter}  for true identity
 * @author Sinkevych Olena
 *
 */
@WebFilter(urlPatterns = { "/*" })
public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Role role = (Role) session.getAttribute(ControllerConstants.ROLE);

        if (role == null&&!req.getServletPath().equals("/login")) {
            resp.sendRedirect("/hospital/login?command=login");
            return;
        }

        else if(role==Role.DOCTOR|| role==Role.NURSE){
            if(req.getServletPath().equals("/admin")||req.getServletPath().equals("/editDoctor")||req.getServletPath().equals("/editPatient")) {
                Integer userId = (Integer) session.getAttribute("user_id");
                resp.sendRedirect("/hospital/medic?command=medic&doctor_id="+userId);
                return;
            }
        }
        // pass the request along the filter chain
        chain.doFilter(request, response);
    }
}
