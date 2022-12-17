package com.epam.hospital.controller;

import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Person;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
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
                ? new SimpleDateFormat("yyyy-MM-dd hh:mm")
                : new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(string.replace("T"," "));
    }

    public static Person getPerson(HttpServletRequest request) throws ParseException {
        return Person.createPerson(request.getParameter("lastName"),
                request.getParameter("firstName"),
                ControllerUtils.getDateByString(request.getParameter("birthday"), false),
                request.getParameter("email"),
                Gender.MALE);
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
}
