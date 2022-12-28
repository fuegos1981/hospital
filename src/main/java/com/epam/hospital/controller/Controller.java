package com.epam.hospital.controller;

import com.epam.hospital.MessageManager;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = {"/"})
public class Controller extends HttpServlet {
    private static MessageManager currentMessageLocale;

    @Override
    public void init() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        getCurrentLocale(req);
        String page = null;
        try {
            if (!isDownLoad(req, resp)) {
                ActionFactory client = new ActionFactory();
                ActionCommand command = client.defineCommand(req);
                page = command.execute(req, currentMessageLocale);

                if (page != null) {
                    if (page.contains(".jsp")) {
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                        dispatcher.forward(req, resp);
                    } else {
                        if (!page.isEmpty())
                            resp.sendRedirect(page);
                    }
                } else {
                    throw new Exception("Проблема не указана команда на странице");
                }
            }
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
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

    private boolean isDownLoad(HttpServletRequest req, HttpServletResponse resp) throws DBException, SQLException {
        if (req.getParameter("download") != null) {
            try {
                ControllerUtils.downloadHistory(req, resp);
                //resp.sendRedirect("/hospital/readPatient?id=2&command=patient_info");
            } catch (ValidateException e) {
                //e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}
