package com.epam.hospital.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * An implementation of the Filter interface to change the encoding of the request and response to the encoding specified by the filter parameter.
 *Please see the {@link javax.servlet.annotation.WebFilter}  for true identity
 * @author Sinkevych Olena
 *
 */
@WebFilter(urlPatterns = { "/*" })
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}
