package com.epam.hospital.controller;

import com.epam.hospital.HistoryPatient;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ControllerUtils {

    public static void setCookie(HttpServletResponse resp, String name, String value) {

        Cookie c = new Cookie(name, value);
        c.setMaxAge(60 * 60); // время жизни файла cookie
        resp.addCookie(c); // добавление cookie к объекту-ответу

    }

    public static ArrayList<String> addToRequest(HttpServletRequest request) {
        ArrayList<String> messages = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            messages.add("Number cookies : " + cookies.length);
            for (Cookie c : cookies) {
                messages.add(c.getName() + " = " + c.getValue());
            }
        }
        return messages;
    }

    public static Date getDateByString(String string, boolean isTime) throws ParseException {
        if (string==null||string.isEmpty()){
            return null;
        }
        SimpleDateFormat sdf = (isTime)
                ? new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.UK)
                : new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(string.replace("T"," "));
    }

    public static void RemoveAttributes(HttpServletRequest request, String... atrs) {
        for (String atr : atrs) {
            request.removeAttribute(atr);
        }
    }

    public static void setAttributes(HttpServletRequest request, String... atrs) {
        for (String atr : atrs) {
            request.setAttribute(atr, request.getParameter(atr));
        }
    }

    public static void setNameFromParameter(HttpServletRequest request) {
        String name = request.getParameter(ControllerConstants.NAME);
        if (name!=null){
            name =name.replace(ControllerConstants.PERC, " ");
            request.setAttribute(ControllerConstants.NAME, name);
        }
    }

    public static Integer parseID(HttpServletRequest request, String idName) {
        String idStr= request.getParameter(idName);
        if (idStr==null|| idStr.isEmpty())
            return null;
        else
        return Integer.valueOf(idStr);
    }

    public static void setGender(HttpServletRequest request) {
        String gender = request.getParameter(ControllerConstants.GENDER);
        gender=(gender==null)?ControllerConstants.MALE:gender;
        request.setAttribute(ControllerConstants.GENDER,gender);
    }

    public static int[] setMasForPagination(HttpServletRequest request, int countList, String currentCountName, String countPageName){

        int currentPage;
        if (request.getParameter(currentCountName)==null||request.getParameter(currentCountName).isEmpty()){
            currentPage=1;
        }
        else {
            currentPage = Integer.parseInt(request.getParameter(currentCountName));
        }

        int countPage = (countList<ControllerConstants.MAX_COUNT_ON_PAGE)?1:(int)Math.ceil(1.00*countList/ControllerConstants.MAX_COUNT_ON_PAGE);
        request.setAttribute(countPageName, countPage);
        request.setAttribute(currentCountName,currentPage);
        if (countList<=ControllerConstants.MAX_COUNT_ON_PAGE){
            return null;
        }
        else{
            return new int[]{(currentPage-1)*ControllerConstants.MAX_COUNT_ON_PAGE,Math.min(currentPage*ControllerConstants.MAX_COUNT_ON_PAGE,countList)};
        }
    }

    static void downloadHistory(HttpServletRequest req, HttpServletResponse resp) throws DBException, SQLException, ValidateException {

        Patient patient = PatientService.getPatientService().readById(ControllerUtils.parseID(req, Fields.PATIENT_ID));
        String  fileURL = HistoryPatient.getHistoryPatient(patient, req.getServletContext().getRealPath("WEB-INF/pdf/info.pdf"));
        final int ARBITARY_SIZE = 1048;
        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition", "attachment; filename=info.pdf");

        try(InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/pdf/info.pdf");
            OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[ARBITARY_SIZE];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
