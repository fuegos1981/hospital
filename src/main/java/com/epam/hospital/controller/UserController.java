package com.epam.hospital.controller;

import com.epam.hospital.db.entity.User;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.UserService;
import com.epam.hospital.service.impl.UserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class UserController extends HttpServlet {
    private static UserService userService;

    @Override
    public void init() throws ServletException {
        userService = UserServiceImp.getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            User user = userService.readByLogin(login);
            req.getRequestDispatcher("WEB-INF/pages/adminInterface.jsp").forward(req,resp);
        } catch (DBException e) {
            //resp.sendRedirect("/error");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
        //resp.getWriter().print("Hello from servlet");
    }

}
