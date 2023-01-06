package com.epam.hospital.controller;

import com.epam.hospital.MessageManager;
import com.epam.hospital.exceptions.DBException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Interface implementations define in the implemented method the business logic for executing the corresponding command.
 * @author Sinkevych Olena
 *
 */
public interface ActionCommand {
    String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException, ParseException;
}
