package com.epam.hospital.controller;

import com.epam.hospital.MessageManager;
import com.epam.hospital.repository.DBException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns={"/"})
public class Controller extends HttpServlet {
    private static MessageManager currentMessageLocale;

    @Override
    public void init() {
        //userService = UserService.getUserService();
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
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            // вызов страницы ответа на запрос
            dispatcher.forward(req, resp);
        } else {
            // установка страницы c cообщением об ошибке
            req.setAttribute("message", "Проблема не  указана команда на странице");
            resp.setStatus(404);
            resp.sendRedirect("/hospital/error");
        }
    }

    private void getCurrentLocale(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String currentLocale =(String) session.getAttribute("locale");
        if (currentLocale==null){
            currentLocale ="en-US";
            session.setAttribute("locale", currentLocale);
        }
        currentMessageLocale = currentLocale.equals("en-US")?MessageManager.EN:MessageManager.UA;
    }

}
