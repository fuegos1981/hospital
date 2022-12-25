package com.epam.hospital.controller;

import com.epam.hospital.MessageManager;
import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = {"/"})
public class Controller extends HttpServlet {
    private static MessageManager currentMessageLocale;
    private static Logger logger = LogManager.getLogger();
    @Override
    public void init() {

        //String prefix = getServletContext().getRealPath("/");
        //String filename = getInitParameter("init_log4j");
        //if (filename != null) {
        //    PropertyConfigurator.configure(prefix + filename);
        //}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("hhhhh");
        getCurrentLocale(req);
        // определение команды, пришедшей из JSP
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(req);
        String page = null;
        try {
            page = command.execute(req, currentMessageLocale);
        } catch (DBException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (page != null) {
            if (page.contains(".jsp")) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(req, resp);
            }
            else resp.sendRedirect(page);

        } else {
            // установка страницы c cообщением об ошибке
            req.setAttribute("message", "Проблема не  указана команда на странице");
            resp.setStatus(404);
            resp.sendRedirect("/hospital/error");
        }
    }

    private void getCurrentLocale(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        if (req.getParameter(ControllerConstants.SUBMIT_US) != null) {
            session.setAttribute(ControllerConstants.LOCALE, ControllerConstants.LOCALE_US);
        } else if (req.getParameter(ControllerConstants.SUBMIT_UA) != null) {
            session.setAttribute(ControllerConstants.LOCALE, ControllerConstants.LOCALE_UA);
        }
        String currentLocale = (String) session.getAttribute(ControllerConstants.LOCALE);
        if (currentLocale == null || currentLocale.isEmpty()) {
            currentLocale = ControllerConstants.LOCALE_US;
            session.setAttribute(ControllerConstants.LOCALE, currentLocale);
        }
        currentMessageLocale = currentLocale.equals(ControllerConstants.LOCALE_US) ? MessageManager.EN : MessageManager.UA;
    }

}
