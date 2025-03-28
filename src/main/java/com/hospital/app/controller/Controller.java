package com.hospital.app.controller;

import com.hospital.app.MessageManager;
import com.hospital.app.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Implementation of HttpServlet,used to create an HTTP servlet suitable for a Website Hospital
 * <p>
 * Please see the {@link javax.servlet.http.HttpServlet} class for true identity
 *
 * @author Sinkevych Olena
 */
@WebServlet(urlPatterns = {"/"})
public class Controller extends HttpServlet {
    // Property - current locale, used to display error messages in the given locale
    private MessageManager currentMessageLocale;
    private final static Logger logger = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    /**
     * <p>This method is an implementation of the methods doGet() and doPost()
     * </p>
     *
     * @param req  {@link HttpServletRequest} is as an argument to the servlet's service methods (doGet, doPost...).
     * @param resp {@link HttpServletResponse} is as an argument to the servlet's service methods (doGet, doPost...).
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getCurrentLocale(req);
        String page;
        try {
            if (!isDownLoad(req, resp)) {
                ActionFactory client = new ActionFactory();
                ActionCommand command = client.defineCommand(req);
                page = command.execute(req, currentMessageLocale);
                if (page != null) {
                    if (page.contains(".jsp")) {
                        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
                        dispatcher.forward(req, resp);
                    } else {
                        if (!page.isEmpty())
                            resp.sendRedirect(page);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(req.getSession().getId()+"; "+e.getMessage());
            req.setAttribute("message", currentMessageLocale.getString("something_goes_wrong"));
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(ControllerConstants.PAGE_ERROR);
            dispatcher.forward(req, resp);
        }

    }

    /**
     * <p>This method sets the selected locale, or sets a default</p>
     *
     * @param req is as an argument to the servlet's service methods (doGet, doPost...).
     */
    public void getCurrentLocale(HttpServletRequest req) {
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

    /**
     * <p>This method determines if a patient history should be loaded and performs it if necessary.
     * </p>
     *
     * @param req  is as an argument to the servlet's service methods (doGet, doPost...).
     * @param resp is as an argument to the servlet's service methods (doGet, doPost...).
     * @return Returns true if the download was successful
     */
    protected boolean isDownLoad(HttpServletRequest req, HttpServletResponse resp) throws DBException, SQLException {
        if (req.getParameter("download") != null) {
            ControllerUtils.downloadHistory(req, resp, currentMessageLocale);
            return true;
        }
        return false;
    }

}
