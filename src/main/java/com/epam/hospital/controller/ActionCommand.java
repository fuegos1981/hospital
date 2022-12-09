package com.epam.hospital.controller;

import com.epam.hospital.MessageManager;
import com.epam.hospital.repository.DBException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface ActionCommand {
    String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException;
}
